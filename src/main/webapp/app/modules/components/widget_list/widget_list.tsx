import React, { Component, ReactElement, useState, useEffect } from 'react';
import { Row, Col, Alert } from 'reactstrap';
import WidgetContainer from '../widget_container/widget_container';
import AddWidget from '../add_widget/add_widget';
import AddModal from '../add_widget/add_widget_modal';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import SearchBar from '../search_bar/search_bar';
import { IWidget } from 'app/shared/model/widget.model';

export interface IWidgetListProps {
  widgets: readonly IWidget[];
}

export const WidgetList = (props: IWidgetListProps) => {
  const dispatch = useAppDispatch();
  const showAddModal = useAppSelector(state => state.authentication.showAddModal);
  const [showModal, setShowModal] = useState(showAddModal);
  const loading = useAppSelector(state => state.widget.loading);

  const addOnClick = () => {
    setShowModal(true);
  };
  const handleClose = () => {
    setShowModal(false);
  };
  const renderHelper = widget => {
    // refactor to one widget and use a .map on the widgets property
    /*
    const jsx = [];
    let tempKey = 0;
    for (const widget of widgets) {
      jsx.push(<WidgetContainer key={tempKey}>{widget}</WidgetContainer>);
      tempKey++;
    }
    return jsx;
    */
    const widgetProps = JSON.parse(widget.props);
    if (widget.type === 'SearchBar') {
      return <WidgetContainer>{React.createElement(SearchBar, widgetProps)}</WidgetContainer>;
    }
  };

  return (
    <div>
      <AddModal showModal={showModal} handleClose={handleClose}></AddModal>
      <Row>
        {props.widgets.map(renderHelper)}
        <WidgetContainer>
          <AddWidget onClick={addOnClick}></AddWidget>
        </WidgetContainer>
      </Row>
    </div>
  );
};

export default WidgetList;
