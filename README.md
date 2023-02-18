# JDBC + DAO

---

Objetivo geral:
- Conhecer os principais recursos do JDBC na teoria e prática 
- Elaborar a estrutura básica de um projeto com JDBC
- Implementar o padrão DAO manualmente com JDBC

## Visão geral do JDBC
- JDBC (Java Database Connectivity): API padrão do Java para acesso a dados          
- Páginas oficiais:
- https://docs.oracle.com/javase/8/docs/technotes/guides/jdbc/
- https://docs.oracle.com/javase/8/docs/api/java/sql/package-summary.html
- Pacotes: java.sql e javax.sql (API suplementar para servidores)

Demo: inserir dados

API:
- PreparedStatement 
- executeUpdate
- Statement.RETURN_GENERATED_KEYS 
- getGeneratedKeys


Checklist:
- Inserção simples com preparedStatement
- Inserção com recuperação de Id

Demo: atualizar dados

Demo: deletar dados
Código fonte: https://github.com/acenelio/jdbc5 Checklist:
- Criar DbIntegrityException
- Tratar a exceção de integridade referencial
Demo: transações
Referências: https://www.ibm.com/support/knowledgecenter/en/SSGMCP_5.4.0/product-overview/acid.html 

Código fonte: 
API:
- setAutoCommit(false)
- commit()
- rollback()

---

### findAll implementation

~~~~SQL

SELECT seller.*,department.Name as DepName FROM seller INNER JOIN department 
ON seller.DepartmentId = department.Id ORDER BY Name

~~~~

### insert implementation

~~~~SQL
INSERT INTO seller
(Name, Email, BirthDate, BaseSalary, DepartmentId) VALUES 
(?, ?, ?, ?, ?)
~~~~

### update implementation

~~~~SQL
UPDATE seller 
SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? WHERE Id = ?
~~~~
### delete implementation
~~~~SQL
DELETE FROM seller 
WHERE Id = ?
~~~~

