import { Component, OnInit, ElementRef, Renderer2, ViewChild, AfterViewInit, OnDestroy } from '@angular/core';
import {DataService} from "../../data.service";
import {ElementService} from "../../element.service";
import {Data} from "../../data";
import {Element} from "../../element";
import { Router } from '@angular/router';
interface AutoCompleteCompleteEvent {
  originalEvent: Event;
  query: string;
}

interface MyModel {
  result: string;
  x: string;
  y: string;
  r: string;
  time: string;
  scriptTime: string;
}

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit, AfterViewInit, OnDestroy {

  modelList: MyModel[] = [];
  valuesX: string[] = [];
  valuesR: string[] = [];
  filteredValuesX: any[] = [];
  filteredValuesR: any[] = [];
  isTextVisible = false;
  errorMessage = "";
  showR = "";
  @ViewChild('svgElement') svgElement!: ElementRef<SVGSVGElement>;

  ngAfterViewInit(): void {
    this.setupSvgClickEvent();
  }

  private setupSvgClickEvent(): void {
    this.renderer.listen(this.svgElement.nativeElement, 'click', (event: MouseEvent) => {
      const R_button = this.element.r;
      const x = event.offsetX;
      const y = event.offsetY;
      const R = R_button;

      if (!this.isTextVisible && R != "" && Number(R) > 0){
        const normalizedX = (((x - 200) * 2 * Number(R)) / 300).toFixed(2);
        const normalizedY = (((200 - y) * 2 * Number(R)) / 300).toFixed(2);

        console.log(`x: ${x}, normX: ${normalizedX}`);
        console.log(`y: ${y}, normY: ${normalizedY}`);
        console.log(Number(R));
        this.element.x = normalizedX;
        this.element.y = normalizedY;
        this.element.r = R;
        this.onSubmit()
      }else{
        console.log("R problem");
      }
    });
  }

  private checker(x: number, y: number, r: number): boolean {
    let resultF = false;


    //прямоугольник
    if (x <= 0 && y <= 0) {
        if ( (x >= (-r/2)) && (y >= (-r)) ) {
          resultF = true;
        }
      }

    //круг
    if (x <= 0 && y >= 0) {
      if ((Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow(r, 2))) {
        resultF = true;

      }
    }
    //треугольник
    if (x>= 0 && y <=0) {
      if (y >= -(r/2) && x<=r/2  && y >=  x - r/2) {
        resultF = true;
      }
    }


    return resultF;
  }

  private drawPoint (normalizedX: String, normalizedY: String, R: String){
    const point = document.createElementNS('http://www.w3.org/2000/svg', 'circle');

    const x: Number = Number(normalizedX) * 300 / Number(R) / 2 + 200;
    const y: Number = 200 - Number(normalizedY) * 300 / Number(R) / 2;
    point.setAttribute('cx', String(x));
    point.setAttribute('cy', String(y));
    point.setAttribute('r', '3');
    point.setAttribute('class', 'graph-point');

    const checkStatus = this.checker(Number(normalizedX), Number(normalizedY), Number(R));

    if (checkStatus) {
      point.setAttribute('fill', 'white');
    } else {
      point.setAttribute('fill', '#e42575');
    }

    this.renderer.appendChild(this.svgElement.nativeElement, point);
  }

  constructor(private dataService: DataService, public data: Data, public element: Element, private elementService: ElementService, private renderer: Renderer2, private router: Router) {
    this.modelList = [];

    this.valuesX = ['-2', '-1.5', '-1', '-0.5', '0', '0.5', '1.0', '1.5', '2.0'];
    this.valuesR = ['0.5', '1.0', '1.5', '2.0'];
  }

  ngOnInit() {
    if (!localStorage.getItem('sessionId')){
      this.router.navigate(['/']);
    }else{
      this.loadAll();
    }

  }

  ngOnDestroy() {
    console.log("destroy everything");
  }

  filterValX(event:  AutoCompleteCompleteEvent) {
    let filtered: any[] = [];
    let query = event.query;

    for (let i = 0; i < this.valuesX.length; i++) {
      let value = this.valuesX[i];
      if (value.indexOf(query.toLowerCase()) == 0) {
        filtered.push(value);
      }
    }
    this.filteredValuesX = filtered;
  }






  filterValR(event: AutoCompleteCompleteEvent) {
    let filtered: any[] = [];
    let query = event.query;

    for (let i = 0; i < this.valuesR.length; i++) {
      let value = this.valuesR[i];
      if (value.indexOf(query.toLowerCase()) == 0) {
        filtered.push(value);
      }
    }
    this.filteredValuesR = filtered;
  }

  checkInput() {
    const valueX = parseFloat(this.element.x);
    const valueY = parseFloat(this.element.y);
    const valueR = parseFloat(this.element.r);
    if (this.element.x.length > 10 || this.element.y.length > 10 || this.element.r.length > 10){
      this.errorMessage = "Max length is 10!";
      this.isTextVisible = true;
    }else if((isNaN(valueX) || valueX < -2 || valueX > 2) && this.element.x != ""){
      this.errorMessage = "Something wrong with X!";
      this.isTextVisible = true;
    }else if((isNaN(valueY) || valueY < -3 || valueY > 3) && this.element.y != ""){
      this.errorMessage = "Something wrong with Y!";
      this.isTextVisible = true;
    }else if((isNaN(valueR) || valueR <= 0 || valueR > 2) && this.element.r != ""){
      this.errorMessage = "Something wrong with R!";
      this.isTextVisible = true;
    }else {
      this.isTextVisible = false;
    }
  }

  clearModel(){
    this.elementService.clearAllElements(this.element).subscribe(
      (response) => {
        this.modelList.splice(0, this.modelList.length);
        const points = this.svgElement.nativeElement.querySelectorAll('.graph-point');
        points.forEach(point => {
          this.renderer.removeChild(this.svgElement.nativeElement, point);
        });
        console.log('cleared');
      },
      (error) => {
        console.error('Error sending data', error);
      }
    );
  }

  logOut(){
    this.dataService.logoutUser(this.data).subscribe(
        (response) => {
          localStorage.removeItem("sessionId");
          this.router.navigate(['/']);
        },
        (error) => {
          console.error("Can't logout", error);
        }
    );
  }

  graphChange(event: any){
    const valueR = parseFloat(this.element.r);
    this.checkInput()
    if(!((isNaN(valueR) || valueR <= 0 || valueR > 2) && this.element.r != null)){
      const points = this.svgElement.nativeElement.querySelectorAll('.graph-point');
      points.forEach(point => {
        this.renderer.removeChild(this.svgElement.nativeElement, point);
      });
      this.modelList.forEach((el: MyModel) => {
        console.log(el.x);
        console.log(el.y);
        console.log(this.element.r);
        this.showR = valueR.toString();
        this.drawPoint(el.x, el.y, this.element.r);
      });

    }
  }

  loadAll() {
    console.log("Load all points");
    this.elementService.getAllElements(this.element).subscribe(
        (response) => {
          console.log('Data sent successfully', response);
          const pointsData = response.pointsData?.point || [];
          // @ts-ignore
          const pointsArray = pointsData.map(point => ({
            result: point.result,
            x: point.x,
            y: point.y,
            r: point.r,
            time: point.time,
            scriptTime: point.executionTime
          }));
          this.modelList = [...this.modelList, ...pointsArray];
          this.modelList.forEach(point => {
            this.drawPoint(point.x, point.y, point.r);
          });
          console.log(this.modelList);
        },
        (error) => {
          console.error('Error sending data', error);
        }
    );
  }

  onSubmit(){
    if (!this.isTextVisible){
      console.log(this.element);
      this.elementService.addElement(this.element).subscribe(
        (response) => {
          console.log('Data sent successfully', response);
          if (response.statusCode == 201){
            this.drawPoint(this.element.x, this.element.y, this.element.r);
            const pointData = response.pointsData?.point;
            if (pointData) {
              const point: MyModel = {
                result: pointData.result,
                x: pointData.x,
                y: pointData.y,
                r: pointData.r,
                time: pointData.time,
                scriptTime: pointData.executionTime
              };

              this.modelList.push(point);
            }
          }
        },
        (error) => {
          console.error('Error sending data', error);
        }
      );
    }else{
      console.log("error");
    }
  }

}
