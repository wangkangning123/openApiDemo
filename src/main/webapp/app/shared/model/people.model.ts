import { Moment } from 'moment';

export interface IPeople {
  id?: number;
  username?: string;
  password?: string;
  email?: string;
  phone?: string;
  date?: Moment;
  sex?: string;
  hobby?: any;
}

export class People implements IPeople {
  constructor(
    public id?: number,
    public username?: string,
    public password?: string,
    public email?: string,
    public phone?: string,
    public date?: Moment,
    public sex?: string,
    public hobby?: any
  ) {}
}
