import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import UserWidgets from './user-widgets';
import UserWidgetsDetail from './user-widgets-detail';
import UserWidgetsUpdate from './user-widgets-update';
import UserWidgetsDeleteDialog from './user-widgets-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={UserWidgetsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={UserWidgetsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={UserWidgetsDetail} />
      <ErrorBoundaryRoute path={match.url} component={UserWidgets} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={UserWidgetsDeleteDialog} />
  </>
);

export default Routes;
