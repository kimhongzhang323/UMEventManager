# Build stage
FROM node:20-alpine AS builder
WORKDIR /app

COPY package*.json ./
RUN npm ci

COPY . .
# Using standard Angular/Vite builder output
RUN npm run build -- --configuration=production

# Serve stage
FROM nginx:alpine
# Copy built assets
# (assuming Angular 17+ outputs to dist/frontend/browser, adjust if needed)
COPY --from=builder /app/dist/frontend/browser /usr/share/nginx/html

# SPA Routing: Fallback all requests to index.html
RUN sed -i 's/try_files $uri $uri\/ \/index.html;/g' /etc/nginx/conf.d/default.conf || \
    echo 'server { listen 80; location / { root /usr/share/nginx/html; index index.html; try_files $uri $uri/ /index.html; } }' > /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]