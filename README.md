# Alunos:
## Henrique Dias Militão
## Vagner Simone Martins Filho

# Visão Geral
A entrega consiste em um sistema que gerencia pedidos de itens de cantina, como salgados, sucos e refrigerantes. A cantina pode ilustrar seus produtos com fotos da galeria ou da câmera. E os clientes podem reservar seus pedidos com horário para retirada e tolerância de atraso.

# Papéis
## Cliente:
Usuário capaz de montar um pedido com n itens e marcar horário de retirada com tolerância de atraso de até 30 minutos. Deve também ser capaz de escolher de qual fornecedor quer consumir dentre uma lista de fornecedores.

## Fornecedor:
Usuário que cria uma cantina para atender seus clientes. A cantina deverá dispor de um catálogo com os itens disponíveis, os quais possuem preço, descrição e, opcionalmente, uma ilustração. A ilustração pode ser da galeria ou tirada diretamente da câmera.

# Requisitos Funcionais:
## RF-1 Criar conta cliente:
O app deve permitir o cadastro de usuário cliente diante do fornecimento dos dados:
CPF, nome, sobrenome, e-mail e senha.

## RF-2 Criar conta fornecedor:
O app deve permitir o cadastro de usuário fornecedor diantofornecimento dos dados:
CPF, nome, sobrenome, nome e localização da cantina, e-mail e senha.

## RF-3 Autenticar usuário:
O app não deve permitir o acesso e navegação de usuários não autenticados.

## RF-4 Entrar no app:
O app deve permitir o acesso de usuários cadastrados mediante email e senha existentes.

## RF-5 Listar cantinas:
O app deve listar todos os fornecedores para um cliente.

## RF-6 Ver catálogo da cantina selecionada:
O cliente deve ser capaz de ver o catálogo da cantina selecionada dentre as disponíveis na lista.

## RF-7 Escolher item:
O cliente deve ser capaz de escolher o item do catálogo da cantina, mudar sua quantidade e adicionar no pedido.

## RF-8 Fazer pedido:
O cliente deve ser capaz de fazer o pedido para a cantina, definindo quando irá buscar e uma tolerância de atraso de até 30 minutos.

## RF-9 Cancelar pedido:
O cliente pode cancelar um pedido desde que falte mais de 1 hora para buscá-lo.

## RF-10 Adicionar item no catálogo:
O fornecedor deve ser capaz de adicionar um item com um nome, preço, descrição, quantidade e, opcionalmente, uma ilustração, que pode ser uma foto da galeria ou diretamente da câmera do celular.

## RF-11 Mostrar disponibilidade:
O app deve mostrar para o cliente quantas unidades de um item do catálogo estão disponíveis.

## RF-12 Bloquear item:
O app deve impedir que o cliente insira um item cujo quantidade seja 0 em um pedido.

## RF-13 Remover item do catálogo:
O fornecedor deve ser capaz de remover items do catálogo.

## RF-14 Editar item do catálogo:
O fornecedor deve ser capaz de editar um item do catálogo, alterando todos os dados que o item possui.

## RF-15 Negar pedido:
O fornecedor deve poder negar o pedido de um cliente.

## RF-16 Aceitar pedido:
O fornecedor deve poder aceitar o pedido de um cliente.

## RF-17 Notificar cliente sobre status do pedido:
O app deve notificar o cliente de assim que o status (negado ou aceito) do seu pedido for definido.

## RF-18 Finalizar pedido:
O app deve permitir que o usuário finalize o pedido, marcando-o como entregue ou não.

## RF-19 Sair do app:
O app deve permitir que os usuários saiam da aplicação.
