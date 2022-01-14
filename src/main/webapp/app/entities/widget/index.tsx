import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Widget from './widget';
import WidgetDetail from './widget-detail';
import WidgetUpdate from './widget-update';
import WidgetDeleteDialog from './widget-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={WidgetUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={WidgetUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={WidgetDetail} />
      <ErrorBoundaryRoute path={match.url} component={Widget} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={WidgetDeleteDialog} />
  </>
);

export default Routes;
