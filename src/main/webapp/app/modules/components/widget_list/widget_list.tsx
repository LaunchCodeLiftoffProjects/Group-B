import React, { Component, ReactElement } from 'react';
import { Row, Col, Alert } from 'reactstrap';

export interface IWidgetListProps {
  widgets: ReactElement[];
}

export const WidgetList = (props: IWidgetListProps) => {
  // let jsx: String  = '';

  return <div>{props.widgets.map(widget => widget)}</div>;
};

export default WidgetList;
