import React from 'react';
import { Row, Col, Alert } from 'reactstrap';

export interface ISearchBarProps {
  action: string;
}

export const SearchBar = (props: ISearchBarProps) => {
  return (
    <div>
      <form action={props.action}>
        <input type="text" name="q"></input>
        <button type="submit" className="btn btn-primary">
          Search
        </button>
      </form>
    </div>
  );
};

export default SearchBar;
