import { Component, OnInit, OnDestroy } from '@angular/core';

@Component({
  selector: 'app-start',
  templateUrl: './start.component.html',
  styleUrl: './start.component.css'
})

export class StartComponent implements OnInit, OnDestroy{
  private intervalId: any;
  constructor() {
  }
  ngOnInit(): void {
    this.updateTime();
    this.intervalId = setInterval(() => this.updateTime(), 1000);
  }

  ngOnDestroy(): void {
    clearInterval(this.intervalId);
  }

  updateTime(): void {
    const now: Date = new Date();
    const clock: HTMLElement | null = document.getElementById("clock");

    if (clock) {
      clock.innerHTML = now.toLocaleTimeString() + " " + now.toLocaleDateString();
    }
  }
}
