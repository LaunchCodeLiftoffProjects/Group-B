import './home.scss';

import React from 'react';

import { Link, Redirect } from 'react-router-dom';

import { Row, Col, Alert } from 'reactstrap';

import { useAppSelector } from 'app/config/store';

import SearchBar from '../components/search_bar/search_bar';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  if (!account?.login) {
    return <Redirect to="/login" />;
  }
  return (
    <div>
      <SearchBar action="http://www.google.com/search?"></SearchBar>
    </div>
  );
};

export default Home;
