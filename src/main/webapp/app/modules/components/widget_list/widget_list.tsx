import React, { Component, ReactElement, useState, useEffect } from 'react';
import { Row, Col, Alert } from 'reactstrap';
import WidgetContainer from '../widget_container/widget_container';
import AddWidget from '../add_widget/add_widget';
import AddModal from '../add_widget/add_widget_modal';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export interface IWidgetListProps {
  widgets: ReactElement[];
}

export const WidgetList = (props: IWidgetListProps) => {
  const dispatch = useAppDispatch();
  const showAddModal = useAppSelector(state => state.authentication.showAddModal);
  const [showModal, setShowModal] = useState(showAddModal);

  const addOnClick = () => {
    setShowModal(true);
  };
  const handleClose = () => {
    setShowModal(false);
  };
  const renderHelper = widgets => {
    const jsx = [];
    let tempKey = 0;
    for (const widget of widgets) {
      jsx.push(<WidgetContainer key={tempKey}>{widget}</WidgetContainer>);
      tempKey++;
    }
    return jsx;
  };

  return (
    <div>
      <AddModal showModal={showModal} handleClose={handleClose}></AddModal>
      <Row>
        {renderHelper(props.widgets)}
        <WidgetContainer>
          <AddWidget onClick={addOnClick}></AddWidget>
        </WidgetContainer>
      </Row>
    </div>
  );
};

export default WidgetList;
