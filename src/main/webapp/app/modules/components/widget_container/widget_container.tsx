import React from 'react';
import { Row, Col, Alert } from 'reactstrap';

export const WidgetContainer = (props: any) => {
  return (
    <div className="card" style={{ width: '18rem' }}>
      <div className="card-body">{props.children}</div>
    </div>
  );
};
export default WidgetContainer;
