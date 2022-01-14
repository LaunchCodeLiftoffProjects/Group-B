import { IUser } from 'app/shared/model/user.model';
import { IWidget } from 'app/shared/model/widget.model';

export interface IUserWidgets {
  id?: number;
  user?: IUser | null;
  widgets?: IWidget[] | null;
}

export const defaultValue: Readonly<IUserWidgets> = {};
