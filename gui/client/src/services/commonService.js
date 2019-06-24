const parseNumber = number =>
  number.replace(/(\d{3})(\d{2})(\d{3})(\d{2})(\d{2})/, "+$1 ($2) $3-$4-$5");

const getToken = () =>
  JSON.parse(localStorage.getItem("user")) ||
  JSON.parse(sessionStorage.getItem("user"));

export const commonService = {
  parseNumber,
  getToken
};
