export interface IPeople {
  id?: number;
  username?: string;
  password?: string;
  email?: string;
  phone?: string;
}

export class People implements IPeople {
  constructor(public id?: number, public username?: string, public password?: string, public email?: string, public phone?: string) {}
}
