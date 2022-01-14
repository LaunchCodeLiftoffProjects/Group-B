import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IUserWidgets } from 'app/shared/model/user-widgets.model';
import { getEntities as getUserWidgets } from 'app/entities/user-widgets/user-widgets.reducer';
import { getEntity, updateEntity, createEntity, reset } from './widget.reducer';
import { IWidget } from 'app/shared/model/widget.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const WidgetUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const userWidgets = useAppSelector(state => state.userWidgets.entities);
  const widgetEntity = useAppSelector(state => state.widget.entity);
  const loading = useAppSelector(state => state.widget.loading);
  const updating = useAppSelector(state => state.widget.updating);
  const updateSuccess = useAppSelector(state => state.widget.updateSuccess);
  const handleClose = () => {
    props.history.push('/widget');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getUserWidgets({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...widgetEntity,
      ...values,
      userwidgets: userWidgets.find(it => it.id.toString() === values.userwidgets.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...widgetEntity,
          userwidgets: widgetEntity?.userwidgets?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="customHomePageApp.widget.home.createOrEditLabel" data-cy="WidgetCreateUpdateHeading">
            Create or edit a Widget
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="widget-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Type" id="widget-type" name="type" data-cy="type" type="text" />
              <ValidatedField label="Props" id="widget-props" name="props" data-cy="props" type="text" />
              <ValidatedField id="widget-userwidgets" name="userwidgets" data-cy="userwidgets" label="Userwidgets" type="select">
                <option value="" key="0" />
                {userWidgets
                  ? userWidgets.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/widget" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default WidgetUpdate;
