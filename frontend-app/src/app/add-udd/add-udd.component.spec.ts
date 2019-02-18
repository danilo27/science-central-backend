import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddUddComponent } from './add-udd.component';

describe('AddUddComponent', () => {
  let component: AddUddComponent;
  let fixture: ComponentFixture<AddUddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddUddComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddUddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
