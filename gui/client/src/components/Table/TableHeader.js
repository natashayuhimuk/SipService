import React from "react";

import TableHead from "@material-ui/core/TableHead";
import TableSortLabel from "@material-ui/core/TableSortLabel";
import TableCell from "@material-ui/core/TableCell";
import TableRow from "@material-ui/core/TableRow";
import Checkbox from "@material-ui/core/Checkbox";

const TableHeader = ({
  onRequestSort,
  onSelectAllClick,
  order,
  orderBy,
  numSelected,
  rowCount,
  rows
}) => {
  const createSortHandler = property => event => {
    onRequestSort(event, property);
  };

  return (
    <TableHead>
      <TableRow>
        <TableCell padding="checkbox">
          <Checkbox
            indeterminate={numSelected > 0 && numSelected < rowCount}
            checked={numSelected === rowCount && numSelected !== 0}
            onChange={onSelectAllClick}
          />
        </TableCell>
        {rows.map(row => (
          <TableCell
            key={row.id}
            sortDirection={orderBy === row.id ? order : false}
            style={{ whiteSpace: "nowrap" }}
          >
            <TableSortLabel
              active={orderBy === row.id}
              direction={order}
              onClick={createSortHandler(row.id)}
            >
              {row.label}
            </TableSortLabel>
          </TableCell>
        ))}
      </TableRow>
    </TableHead>
  );
};

export default TableHeader;
