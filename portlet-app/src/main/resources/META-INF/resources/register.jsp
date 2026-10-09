<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
  <head>
    <title>Cadastro</title>
  </head>
  <body>
    <h2>Cadastro</h2>
    <form method="post" action="http://localhost:8085/api/users">
      <input type="hidden" name="role" value="USER" />
      <label>Nome: <input type="text" name="name" required /></label><br />
      <label>E-mail: <input type="email" name="email" required /></label><br />
      <label>Senha: <input type="password" name="password" required /></label><br />
      <button type="submit">Cadastrar</button>
    </form>
  </body>
</html>
