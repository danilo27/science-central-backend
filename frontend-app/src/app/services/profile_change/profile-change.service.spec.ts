import { TestBed, inject } from '@angular/core/testing';

import { ProfileChangeService } from './profile-change.service';

describe('ProfileChangeService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ProfileChangeService]
    });
  });

  it('should be created', inject([ProfileChangeService], (service: ProfileChangeService) => {
    expect(service).toBeTruthy();
  }));
});
