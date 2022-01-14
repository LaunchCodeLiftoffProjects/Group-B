import React from 'react';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter, Alert, Row, Col, Form } from 'reactstrap';

export interface IAddModalProps {
  showModal: boolean;
  handleClose: () => void;
}
export const AddModal = (props: IAddModalProps) => {
  return (
    <div>
      <Modal isOpen={props.showModal}>
        <ModalHeader>Add Widget</ModalHeader>
        <ModalBody>List Widgets Here</ModalBody>
        <ModalFooter>
          <button className="btn btn-primary" onClick={props.handleClose}>
            Close
          </button>
        </ModalFooter>
      </Modal>
    </div>
  );
};

export default AddModal;
