import { TestBed } from '@angular/core/testing';

import { UserRegistering } from './user-registering.service';

describe('UserRegistering', () => {
  let service: UserRegistering;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserRegistering);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
