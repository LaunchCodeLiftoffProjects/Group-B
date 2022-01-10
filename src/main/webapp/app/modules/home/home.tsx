import './home.scss';

import React from 'react';

import { Link, Redirect } from 'react-router-dom';

import { Row, Col, Alert } from 'reactstrap';

import { useAppSelector } from 'app/config/store';

import SearchBar from '../components/search_bar/search_bar';
import WidgetList from '../components/widget_list/widget_list';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  if (!account?.login) {
    return <Redirect to="/login" />;
  }
  return (
    <div>
      <WidgetList
        widgets={[
          <SearchBar action="http://www.google.com/search?" key={1}></SearchBar>,
          <SearchBar action="https://www.bing.com/search?" key={2}></SearchBar>,
        ]}
      ></WidgetList>
    </div>
  );
};

export default Home;
