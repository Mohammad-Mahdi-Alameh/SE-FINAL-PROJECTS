import { TestBed } from '@angular/core/testing';

import { FoundationService } from './foundation.service';

describe('FoundationService', () => {
  let service: FoundationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FoundationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
