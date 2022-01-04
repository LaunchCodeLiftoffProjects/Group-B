import React from 'react';

export interface ISearchBarProps {
  action: string;
}

export const SearchBar = (props: ISearchBarProps) => {
  return (
    <div>
      <form action={props.action}>
        <input type="text" name="q"></input>
        <input type="submit"></input>
      </form>
    </div>
  );
};

export default SearchBar;
