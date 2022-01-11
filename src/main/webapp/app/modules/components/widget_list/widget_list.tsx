import React, { Component, ReactElement } from 'react';
import { Row, Col, Alert } from 'reactstrap';
import WidgetContainer from '../widget_container/widget_container';
import AddWidget from '../add_widget/add_widget';

export interface IWidgetListProps {
  widgets: ReactElement[];
}

export const WidgetList = (props: IWidgetListProps) => {
  const renderHelper = widgets => {
    const jsx = [];
    let tempKey = 0;
    for (const widget of widgets) {
      jsx.push(<WidgetContainer key={tempKey}>{widget}</WidgetContainer>);
      // jsx += `<WidgetContainer key = ${tempKey}>${widget}</WidgetContainer>`
      tempKey++;
    }
    return jsx;
  };
  return (
    <div>
      {renderHelper(props.widgets)}
      <WidgetContainer>
        <AddWidget></AddWidget>
      </WidgetContainer>
    </div>
  );
};

export default WidgetList;
