import React from "react";

import TablePagination from "@material-ui/core/TablePagination";

const TableFooter = ({
  rowsPerPage,
  page,
  count,
  handleChangePage,
  handleChangeRowsPerPage
}) => (
  <TablePagination
    rowsPerPageOptions={[5, 10, 25, 50]}
    labelRowsPerPage={"Rows"}
    component="div"
    count={count}
    rowsPerPage={rowsPerPage}
    page={page}
    onChangePage={handleChangePage}
    onChangeRowsPerPage={handleChangeRowsPerPage}
  />
);

export default TableFooter;
