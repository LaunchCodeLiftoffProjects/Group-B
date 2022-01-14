import './home.scss';

import React from 'react';

import { Link, Redirect } from 'react-router-dom';

import { Row, Col, Alert } from 'reactstrap';

import { useAppSelector } from 'app/config/store';

import SearchBar from '../components/search_bar/search_bar';
import WidgetList from '../components/widget_list/widget_list';
import WidgetContainer from '../components/widget_container/widget_container';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);
  const widgets = useAppSelector(state => state.widget.entities);

  if (!account?.login) {
    return <Redirect to="/login" />;
  }
  return (
    <div>
      <WidgetList widgets={widgets}></WidgetList>
    </div>
  );
};

export default Home;
