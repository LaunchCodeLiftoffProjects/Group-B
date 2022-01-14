import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './widget.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const WidgetDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const widgetEntity = useAppSelector(state => state.widget.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="widgetDetailsHeading">Widget</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{widgetEntity.id}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{widgetEntity.type}</dd>
          <dt>
            <span id="props">Props</span>
          </dt>
          <dd>{widgetEntity.props}</dd>
          <dt>Userwidgets</dt>
          <dd>{widgetEntity.userwidgets ? widgetEntity.userwidgets.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/widget" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/widget/${widgetEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default WidgetDetail;
