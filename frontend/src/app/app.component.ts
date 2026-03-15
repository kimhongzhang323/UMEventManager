import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ApiService } from './core/api/api.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  minutesText = 'date: 2026-03-15\ndepartment: Sponsorship\nevent: Innovation Hackathon\n- follow up with 4 sponsors\n- finalize media kit';
  currentMonth = new Date().getMonth() + 1;
  eventName = 'Innovation Hackathon';

  minutesResult = '';
  allMinutes = '';
  ragResult = '';
  driveItems = '';
  gmailItems = '';

  constructor(private readonly api: ApiService) {}

  analyzeMinutes(): void {
    this.api.analyzeMinutes(this.minutesText).subscribe((res: unknown) => {
      this.minutesResult = JSON.stringify(res, null, 2);
      this.loadMinutes();
    });
  }

  loadMinutes(): void {
    this.api.getMinutes().subscribe((res: unknown) => {
      this.allMinutes = JSON.stringify(res, null, 2);
    });
  }

  generateRagPlan(): void {
    this.api.generatePlan(this.eventName, this.currentMonth).subscribe((res: unknown) => {
      this.ragResult = JSON.stringify(res, null, 2);
    });
  }

  loadGoogleItems(): void {
    this.api.getDriveItems().subscribe((res: unknown) => {
      this.driveItems = JSON.stringify(res, null, 2);
    });
    this.api.getGmailEvents().subscribe((res: unknown) => {
      this.gmailItems = JSON.stringify(res, null, 2);
    });
  }
}
