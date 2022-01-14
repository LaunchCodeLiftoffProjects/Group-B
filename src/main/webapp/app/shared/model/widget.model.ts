import { IUserWidgets } from 'app/shared/model/user-widgets.model';

export interface IWidget {
  id?: number;
  type?: string | null;
  props?: string | null;
  userwidgets?: IUserWidgets | null;
}

export const defaultValue: Readonly<IWidget> = {};
