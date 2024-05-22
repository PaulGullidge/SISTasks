import React from 'react';

const Filter = ({ setFilter }) => {
  const handleFilterChange = (e) => {
    setFilter(e.target.value);
  };

  return (
    <div className="filter">
      <label>
        Filter by category: &nbsp;
        <input type="text" onChange={handleFilterChange} />
      </label>
    </div>
  );
};

export default Filter;
