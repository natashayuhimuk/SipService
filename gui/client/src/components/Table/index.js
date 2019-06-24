import React, { useState, useEffect } from "react";

import Table from "@material-ui/core/Table";
import Paper from "@material-ui/core/Paper";
import { withStyles } from "@material-ui/core/styles";

import TableFooter from "./TableFooter";
import TableHeader from "./TableHeader";
import TableToolbar from "./TableToolbar";
import TableBody from "./TableBody";

const styles = {
  root: {
    width: "100%"
  },
  tableWrapper: {
    overflowX: "auto"
  }
};

const stableSort = (array, cmp) => {
  const stabilizedThis = array.map((el, index) => [el, index]);
  stabilizedThis.sort((a, b) => {
    const order = cmp(a[0], b[0]);
    if (order !== 0) return order;
    return a[1] - b[1];
  });
  return stabilizedThis.map(el => el[0]);
};

const isNumber = n => !isNaN(parseFloat(n)) && isFinite(n);

const desc = (a, b, orderBy) => {
  if (isNumber(a[orderBy]) && isNumber(b[orderBy])) {
    return +a[orderBy] - +b[orderBy];
  }

  if (b[orderBy] > a[orderBy]) {
    return -1;
  }
  if (b[orderBy] < a[orderBy]) {
    return 1;
  }
  return 0;
};

const getSorting = (order, orderBy) =>
  order === "desc"
    ? (a, b) => desc(a, b, orderBy)
    : (a, b) => -desc(a, b, orderBy);

const EnhancedTable = ({
  classes,
  rows,
  array = [],
  buttons,
  title,
  fieldId
}) => {
  const [order, setOrder] = useState("asc");
  const [orderBy, setOrderBy] = useState(rows[0].id);
  const [selected, setSelected] = useState([]);
  const [page, setPage] = useState(0);
  const [data, setData] = useState(array);
  const [search, setSearch] = useState("");
  const [rowsPerPage, setRowsPerPage] = useState(10);

  useEffect(() => handleSortData(), [search, array]);

  const getDataPage = () =>
    stableSort(data, getSorting(order, orderBy)).slice(
      page * rowsPerPage,
      page * rowsPerPage + rowsPerPage
    );

  const handleRequestSort = (event, property) => {
    const newOrderBy = property;
    let newOrder = "desc";

    if (orderBy === property && order === "desc") {
      newOrder = "asc";
    }
    setOrder(newOrder);
    setOrderBy(newOrderBy);
  };

  const handleSelectAllClick = event => {
    let newSelected = [];
    if (event.target.checked) {
      newSelected = getDataPage().map(n => n[fieldId]);
    }
    setSelected(newSelected);
  };

  const handleClick = (event, id) => {
    const selectedIndex = selected.indexOf(id);
    let newSelected = [];

    if (selectedIndex === -1) {
      newSelected = [...selected, id];
    } else if (selectedIndex === 0) {
      newSelected = [...selected.slice(1)];
    } else if (selectedIndex === selected.length - 1) {
      newSelected = [...selected.slice(0, -1)];
    } else if (selectedIndex > 0) {
      newSelected = [
        ...selected.slice(0, selectedIndex),
        ...selected.slice(selectedIndex + 1)
      ];
    }
    setSelected(newSelected);
  };

  const handleChangePage = (event, page) => {
    setPage(page);
    setSelected([]);
  };

  const handleChangeRowsPerPage = event => {
    setRowsPerPage(event.target.value);
    setSelected([]);
  };

  const handleSortData = () => {
    const data = array.filter(item =>
      rows
        .map(item => item.id)
        .some(
          key =>
            typeof item[key] === "string" &&
            ~item[key].toLowerCase().indexOf(search.toLowerCase())
        )
    );
    setData(data);
    setSelected([]);
  };

  const isSelected = id => selected.indexOf(id) !== -1;

  return (
    <Paper className={classes.root}>
      <TableToolbar
        selected={selected}
        buttons={buttons}
        title={title}
        search={search}
        setSearch={setSearch}
      />
      <div className={classes.tableWrapper}>
        <Table>
          <TableHeader
            numSelected={selected.length}
            order={order}
            orderBy={orderBy}
            onSelectAllClick={handleSelectAllClick}
            onRequestSort={handleRequestSort}
            rowCount={getDataPage().length}
            rows={rows}
          />
          <TableBody
            isSelected={isSelected}
            handleClick={handleClick}
            keys={rows.map(item => item.id)}
            fieldId={fieldId}
            data={getDataPage()}
          />
        </Table>
      </div>
      <TableFooter
        rowsPerPage={rowsPerPage}
        page={page}
        handleChangePage={handleChangePage}
        handleChangeRowsPerPage={handleChangeRowsPerPage}
        count={data.length}
      />
    </Paper>
  );
};

export default withStyles(styles)(EnhancedTable);
