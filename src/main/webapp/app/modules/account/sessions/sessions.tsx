import React, { useEffect } from 'react';
import { Alert, Table, Button } from 'reactstrap';

import { getSession } from 'app/shared/reducers/authentication';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { findAll, invalidateSession } from './sessions.reducer';

export const SessionsPage = () => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getSession());
    dispatch(findAll());
  }, []);

  const doSessionInvalidation = series => () => {
    dispatch(invalidateSession(series));
    dispatch(findAll());
  };

  const refreshList = () => {
    dispatch(findAll());
  };

  const account = useAppSelector(state => state.authentication.account);
  const sessions = useAppSelector(state => state.sessions.sessions);
  const updateSuccess = useAppSelector(state => state.sessions.updateSuccess);
  const updateFailure = useAppSelector(state => state.sessions.updateFailure);

  return (
    <div>
      <h2>
        Active sessions for [<strong>{account.login}</strong>]
      </h2>

      {updateSuccess ? (
        <Alert color="success">
          <strong>Session invalidated!</strong>
        </Alert>
      ) : null}

      {updateFailure ? (
        <Alert color="danger">
          <span>
            <strong>An error has occurred!</strong> The session could not be invalidated.
          </span>
        </Alert>
      ) : null}

      <Button color="primary" onClick={refreshList}>
        Refresh
      </Button>

      <div className="table-responsive">
        <Table className="table-striped">
          <thead>
            <tr>
              <th>IP Address</th>
              <th>User agent</th>
              <th>Date</th>
              <th />
            </tr>
          </thead>

          <tbody>
            {sessions.map((s, index) => (
              <tr key={index}>
                <td>{s.ipAddress}</td>
                <td>{s.userAgent}</td>
                <td>{s.tokenDate}</td>
                <td>
                  <Button color="primary" onClick={doSessionInvalidation(s.series)}>
                    Invalidate
                  </Button>
                </td>
              </tr>
            ))}
          </tbody>
        </Table>
      </div>
    </div>
  );
};

export default SessionsPage;
