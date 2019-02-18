import { TestBed, inject } from '@angular/core/testing';

import { UddService } from './udd.service';

describe('UddService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [UddService]
    });
  });

  it('should be created', inject([UddService], (service: UddService) => {
    expect(service).toBeTruthy();
  }));
});
