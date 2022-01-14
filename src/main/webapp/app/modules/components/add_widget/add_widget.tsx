import React, { Component, ReactElement } from 'react';
import AddModal from './add_widget_modal';

export interface IAddWidgetProps {
  onClick: () => void;
}

export const AddWidget = (props: IAddWidgetProps) => {
  return (
    <div>
      <button className="btn btn-primary" onClick={props.onClick}>
        Add a widget!
      </button>
    </div>
  );
};

export default AddWidget;
