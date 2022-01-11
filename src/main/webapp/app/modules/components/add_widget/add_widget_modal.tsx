import React from 'react';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter, Alert, Row, Col, Form } from 'reactstrap';

export interface IAddModalProps {
  showModal: boolean;
}
export const AddModal = (props: IAddModalProps) => {
  return (
    <div>
      <Modal isOpen={props.showModal}>
        <ModalHeader>Add Widget</ModalHeader>
      </Modal>
    </div>
  );
};

export default AddModal;
