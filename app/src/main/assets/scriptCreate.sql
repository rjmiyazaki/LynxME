CREATE TABLE IF NOT EXISTS Cliente (
  ClienteID           int          PRIMARY KEY,
  RazaoSocial         varchar,
  Fantasia            varchar,
  Endereco            varchar,
  Bairro              varchar,
  Complemento         varchar,
  CEP                 int64,
  Cidade              varchar,
  Fone                varchar,
  CNPJ                int64,
  IE                  varchar,
  Rota                int,
  Ordem               int,
  Tipo                varchar,
  Subcanal            varchar,
  TabelaPadraoID      int,
  TabelaSecundariaID  int,
  Referencia          varchar,
  VolumeAnterior      int,
  VolumeAtual         int,
  Variacao            int,
  VendaZero           int,
  FormaPagamentoID    int,
  VolumeTop10         int,
  ExibePesquisa       int DEFAULT 0,
  Condicao1x1         int DEFAULT 0,
  Televendas          int DEFAULT 0,
  SEFAZRestricao      int DEFAULT 0,
  Atendido            int DEFAULT 0,
  SubcanalConfirmado  int DEFAULT 0,
  PesquisaCerveja     int DEFAULT 0,
  PesqCervEnviado     int DEFAULT 0,
  PesqTBEnviado       int DEFAULT 0,
  PesqEQEnviado       int DEFAULT 0,
  PesqSCEnviado       int DEFAULT 0,
  CampanhaColgate     int DEFAULT 0,
  IsentoTaxaBoleto    int DEFAULT 0,
  IsentoAdicional     int DEFAULT 0,
  Adicional           numeric(19, 8) DEFAULT 0,
  Classificacao       varchar,
  SubcanalID          int,
  IsentoTaxaCartao    int DEFAULT 0,
  RepasseRefPet       int DEFAULT 0,
  RepasseLS           int DEFAULT 0,
  ExibeMedalha        int DEFAULT 0,
  NovaCampanhaColgate int DEFAULT 0,
  NotaPDVNG           numeric(7, 2) DEFAULT -1
);|

CREATE TABLE IF NOT EXISTS Produto(
  ProdutoID        varchar PRIMARY KEY,
  Descricao        varchar,
  Unidade          varchar,
  Fracionador      int,
  Categoria        int,
  UniCaixa         int,
  FamiliaID        int,
  Estoque          int,
  Desconto         numeric(6, 2)
);|

CREATE TABLE IF NOT EXISTS FormaPagamento(
  FormaPagamentoID int         PRIMARY KEY, 
  Descricao        varchar,
  Parcelas         int,
  PrazoMedio       int,
  Taxa             money,
  Adicional        numeric(19, 8)
);|

CREATE TABLE IF NOT EXISTS TipoPedido(
  TipoPedidoID     int         PRIMARY KEY,  
  Descricao        varchar
);|

CREATE TABLE IF NOT EXISTS Rota(
  RotaID           int         PRIMARY KEY,  
  Descricao        varchar
);|

CREATE TABLE IF NOT EXISTS Subcanal(
  SubcanalID      int         PRIMARY KEY,  
  Descricao       varchar
);|

CREATE TABLE IF NOT EXISTS MotivoNaoVenda(
  MotivoID        int         PRIMARY KEY,  
  Descricao       varchar,
  Exibe           boolean,
  SolicitaInfo    boolean,
  Pergunta        varchar
);|

CREATE TABLE IF NOT EXISTS HistoricoVenda(  
  ClienteID       int,  
  DataPedido      smalldatetime,
  Valor           currency
);|

CREATE TABLE IF NOT EXISTS HistoricoVendaItem(
  ClienteID       int,
  DataPedido      smalldatetime,
  ItemID          varchar,
  Item            varchar,
  Quantidade      int,
  ValorUnitario   currency,
  Desconto        numeric(6, 2),
  ValorDesconto   currency,
  ValorLiquido    currency, 
  ValorTotal      currency  
);|

CREATE TABLE IF NOT EXISTS TituloCliente(
  ClienteID        int,
  Tipo             varchar,
  Numero           int, 
  Parcela          int,
  DataEmissao      smalldatetime,
  DataVencimento   smalldatetime,
  DiasAtraso       int,
  ValorTitulo      money,
  ValorCorrigido   money,
  Divisao          varchar
);|

CREATE TABLE IF NOT EXISTS PedidoVenda(
  PedidoID         integer PRIMARY KEY AUTOINCREMENT,  
  ClienteID        int,
  TipoPedidoID     int,
  FormaPagamentoID int,
  DataAbertura     smalldatetime,
  DataEncerramento smalldatetime,
  DataTransmissao  smalldatetime,
  ValorPedido      currency,
  Obs              varchar,
  Latitude         varchar,
  Longitude        varchar,
  VendedorID       int  
);|

CREATE TABLE IF NOT EXISTS PedidoVendaItem(
  PedidoID         int,
  ProdutoID        varchar,
  Quantidade       int,
  PrecoUnitario    currency,
  Desconto         numeric(5, 2),
  Acrescimo        numeric(5, 2),
  ValorDesconto    currency,
  ValorAcrescimo   currency,
  PrecoLiquido     currency,
  ValorTotal       currency,
  Repasse          int,
  
  PRIMARY KEY (PedidoID, ProdutoID)
);|

CREATE TABLE IF NOT EXISTS RegistroNaoVenda(
  ID               integer PRIMARY KEY AUTOINCREMENT,
  ClienteID        int,
  MotivoID         int,
  DataRegistro     smalldatetime,
  DataTransmissao  smalldatetime,
  Latitude         varchar,
  Longitude        varchar,
  Observacao       varchar
);|

CREATE TABLE IF NOT EXISTS Configuracao(
  VendedorID       varchar,
  NomeVendedor     varchar,
  DivisaoID        int,
  HoraInicio       varchar,
  HoraTermino      varchar
);|

CREATE TABLE IF NOT EXISTS Categoria(
  CategoriaID int PRIMARY KEY,
  Descricao   varchar
);| 

CREATE TABLE IF NOT EXISTS Meta(
  Categoria   varchar,
  Meta        numeric(9, 2),
  Realizado   numeric(9, 2),
  Tendencia   numeric(9, 2),
  Percentual  numeric(9, 2)     
);| 

CREATE TABLE IF NOT EXISTS Equipamento(
  ClienteID    int,
  Geko         varchar,
  Modelo       varchar,
  Logomarca    varchar,
  Voltagem     int,
  Meta         numeric(9, 2),
  Realizado    numeric(9, 2),
  Percentual   numeric(6, 2),
  Analise      varchar
);| 

CREATE TABLE IF NOT EXISTS ProdutoPreco(
  ProdutoID    varchar,
  TabelaID     int,
  Valor        currency
);|

CREATE TABLE IF NOT EXISTS ClienteCondicao(
  ClienteID    int,
  CondicaoID   int
);|

CREATE TABLE IF NOT EXISTS Devolucao(
  ClienteID    int,
  Data         smalldatetime,
  ItemID       varchar,
  Item         varchar,
  Quantidade   int,
  Devolucao    int,
  Motivo       varchar
);|

CREATE TABLE IF NOT EXISTS Campanha(
  Linha        varchar,
  Meta         int,
  Realizado    int,
  Faltantes    int
);|

CREATE TABLE IF NOT EXISTS Pesquisa(
  ClienteID   int,
  Data        smalldatetime,
  Tipo        int,
  Checkouts   int,
  CheckoutID  int,
  NumPergunta int
  Resposta    varchar
);|

CREATE TABLE IF NOT EXISTS Configuracao(
  VendedorID       varchar,
  NomeVendedor     varchar
);|

CREATE TABLE IF NOT EXISTS ClienteArrastao(
  ClienteID int,
  Campanha  varchar,
  Categoria varchar
);|

CREATE TABLE IF NOT EXISTS TBQuestionario(
  ClienteID  int,
  Data       smalldatetime,
  Tipo       int,
  Checkouts  int,
  CheckoutID int,
  PerguntaID int,
  Resposta   varchar
);|

CREATE TABLE IF NOT EXISTS ClienteFamiliaRestricao(
  ClienteID int,
  FamiliaID int
);|

CREATE TABLE IF NOT EXISTS EquipamentoPesquisa(
  ClienteID int,
  Geko      varchar,
  Logomarca varchar,
  Data      smalldatetime,
  Presente  varchar,
  Enviado   int DEFAULT 0
);|

CREATE TABLE IF NOT EXISTS SubcanaisPesquisa(
  SubcanalID int,
  Descricao  varchar
);|

CREATE TABLE IF NOT EXISTS ClienteSubcanalPesquisa(
  ClienteID int,
  Subcanal  varchar
);|

CREATE TABLE IF NOT EXISTS PesquisaCerveja(
  ClienteID int,
  Marca     varchar,
  Caixas    float,
  Preco     currency,
  Data      smalldatetime
);|

CREATE TABLE IF NOT EXISTS FlashMeta(
  ID         int,
  Categoria  varchar,
  Meta       float,
  Realizado  float,
  Falta      float,
  Tendencia  float,
  MetaDiaria float,
  Variavel   currency,
  Percentual float
);|

CREATE TABLE IF NOT EXISTS FlashCobertura(
  Titulo varchar,
  Valor  varchar
);|

CREATE TABLE IF NOT EXISTS FlashDevolucao(
  Motivo       varchar,
  Quantidade   int,
  Caixas       float,
  Participacao float
);|

CREATE TABLE IF NOT EXISTS FlashTop10(
  ClienteID    int,
  RazaoSocial  varchar,
  Rota         int,
  MesAtual     float,
  TrimAnterior float,
  AnoAnterior  float
);|

CREATE TABLE IF NOT EXISTS FlashQuedas(
  ClienteID    int,
  RazaoSocial  varchar,
  Rota         int,
  MesAtual     float,
  AnoAnterior  float,
  Queda        float
);|

CREATE TABLE IF NOT EXISTS FlashPE(
  ClienteID    int,
  RazaoSocial  varchar,
  Rota         int,
  Volume       float,
  AnoAnterior  float,
  Variacao     float
);|

CREATE TABLE IF NOT EXISTS FlashZat(
  ClienteID    int,
  RazaoSocial  varchar,
  Rota         int,
  Volume       float,
  AnoAnterior  float,
  Variacao     float
);|

CREATE TABLE IF NOT EXISTS FlashGondola(
  ClienteID    int,
  RazaoSocial  varchar,
  Rota         int,
  Volume       float,
  AnoAnterior  float,
  Variacao     float
);|

CREATE TABLE IF NOT EXISTS FlashPrazoFemsa(
  Categoria    varchar,
  Meta         int,
  Realizado    int,
  Falta        int,
  Diario       int,
  Percentual   int
);|

CREATE TABLE IF NOT EXISTS FlashArrastao(
  Categoria    varchar,
  Meta         int,
  Realizado    int,
  Falta        int,
  Diario       int,
  Percentual   int
);|

CREATE TABLE IF NOT EXISTS FlashDesafio(
  Categoria    varchar,
  Meta         int,
  Realizado    int,
  Falta        int,
  Diario       int,
  Percentual   int
);|

CREATE TABLE IF NOT EXISTS FlashEquipamento(
  Dia        varchar,
  Tipo       varchar,
  Quantidade int,
  BateuMeta  int,
  Percentual flaot
);|

CREATE TABLE IF NOT EXISTS FlashPositivacao(
  Dia         int,
  DiaSemana   varchar,
  Clientes    int,
  Positivados int,
  VendaZero   int,
  Percentual  float
);|

CREATE TABLE IF NOT EXISTS FlashDevolucoes(
  Cliente       varchar,
  Rota          varchar,
  Motivo        varchar,
  DataPedido    smalldatetime,
  DataDevolucao smalldatetime,
  Tipo          varchar,
  Caixas        float,
  Valor         float
);|

CREATE TABLE IF NOT EXISTS FlashBig4(
  Item          varchar,
  Meta          float,
  Realizado     float,
  Faltante      float,
  Tendencia     float
);|

CREATE TABLE IF NOT EXISTS IMEI(
  Numero        varchar
);|

CREATE TABLE IF NOT EXISTS PedidoDivisao(
  Nome          varchar,
  Faturamento   currency,
  Volume        currency,
  Cobertura     numeric(6, 2)
);|

CREATE TABLE IF NOT EXISTS PedidoSetor(
  Nome          varchar,
  Faturamento   currency,
  Volume        currency,
  Cobertura     numeric(6, 2)
);|

CREATE TABLE IF NOT EXISTS PedidoVendedor(
  Nome          varchar,
  Faturamento   currency,
  Volume        currency,
  Cobertura     numeric(6, 2)
);|

CREATE TABLE IF NOT EXISTS SugestaoPedido(
  SubcanalID    int,
  ProdutoID     varchar,
  Grupo         varchar,
  Destacado     int
);|

CREATE TABLE IF NOT EXISTS SugestaoPedidoCliente(
  ClienteID     int,
  ProdutoID     varchar,
  Grupo         varchar,
  Destacado     int
);|

CREATE TABLE IF NOT EXISTS ConfiguracaoCampanhaColgate(
  Minimo        currency,
  Maximo        currency,
  Desconto      numeric(6, 2)
);|

CREATE TABLE IF NOT EXISTS ItemCampanhaColgate(
  ItemID        varchar,
  Desconto      numeric(6, 2)
);|

CREATE TABLE IF NOT EXISTS ItemPedagioCampanhaColgate(
  ItemID        varchar,
  Grupo         int
);|

CREATE TABLE IF NOT EXISTS PDVQuestionario(
  ID          int           PRIMARY KEY,
  Nome        varchar,
  SubcanalID  int
);|

CREATE TABLE IF NOT EXISTS PDVPergunta(
  QuestionarioID int,
  NumeroPergunta int,
  Pergunta       varchar,
  Pontuacao      numeric,
  TipoResposta   varchar
);|

CREATE TABLE IF NOT EXISTS PDVResposta(
  QuestionarioID int,
  TipoID         int,
  ClienteID      int,
  PerguntaID     int,
  Resposta       varchar,
  Data           datetime
);|

CREATE TABLE IF NOT EXISTS ItemDescontoCliente(
  ClienteID      int,
  ItemID         varchar,
  Desconto       numeric
);|

CREATE VIEW IF NOT EXISTS [RetornaCaixas] AS
 SELECT
   SUM(Pvi.[Quantidade] * Pro.Fracionador / Pro.UniCaixa)
 FROM
   PedidoVenda AS Pv
 JOIN
   PedidoVendaItem AS Pvi ON Pv.PedidoID = Pvi.PedidoID
 JOIN
   Produto AS Pro ON Pvi.ProdutoID = Pro.ProdutoID
 WHERE
   Pv.[TipoPedidoID] = 1;|

CREATE VIEW IF NOT EXISTS [viwCliente] AS
 SELECT DISTINCT
  Cl.ClienteID,
  Cl.RazaoSocial,
  Cl.Fantasia,
  Cl.Endereco,
  Cl.Bairro,
  Cl.Complemento,
  Cl.CEP,
  Cl.Cidade,
  Cl.Fone,
  Cl.CNPJ,
  Cl.IE,
  Cl.Rota,
  Cl.Ordem,
  Cl.Tipo,
  Cl.Subcanal,
  Cl.TabelaPadraoID,
  Cl.TabelaSecundariaID,
  Cl.Referencia,
  Cl.VolumeAnterior,
  Cl.VolumeAtual,
  Cl.Variacao,
  Cl.VendaZero,
  CASE WHEN Pv.PedidoID IS NULL THEN 0 ELSE 1 END AS Positivado,
  CASE WHEN Nv.ID IS NULL THEN 0 ELSE 1 END AS NaoVenda,
  CASE WHEN Nv.ID IS NULL THEN '' ELSE Mo.Descricao END AS MotivoNaoVenda,
  Cl.FormaPagamentoID,
  Fo.Descricao AS FormaPagamento,
  Cl.VolumeTop10,
  Cl.ExibePesquisa,
  Cl.Condicao1x1,
  Cl.Televendas,
  Cl.SEFAZRestricao,
  Cl.Atendido,
  Cl.SubcanalConfirmado,
  Cl.PesquisaCerveja,
  Cl.PesqCervEnviado,
  Cl.PesqTBEnviado,
  Cl.PesqEQEnviado,
  Cl.PesqSCEnviado,
  Cl.CampanhaColgate,
  Cl.IsentoTaxaBoleto,
  Cl.IsentoAdicional,
  Cl.Adicional,
  Cl.Classificacao,
  Cl.SubcanalID,
  Cl.IsentoTaxaCartao,
  Cl.RepasseRefPet,
  Cl.RepasseLS,
  Cl.ExibeMedalha,
  Cl.NovaCampanhaColgate,
  Cl.NotaPDVNG
 FROM
  Cliente AS Cl
 JOIN
  FormaPagamento AS Fo ON Cl.FormaPagamentoID = Fo.FormaPagamentoID
 LEFT JOIN
  PedidoVenda AS Pv ON Cl.ClienteID = Pv.ClienteID
 LEFT JOIN
  RegistroNaoVenda AS Nv ON Cl.ClienteID = Nv.ClienteID
 LEFT JOIN
   MotivoNaoVenda AS Mo ON Nv.MotivoID = Mo.MotivoID;|