# Best2bee - XYZ Order APP

A solução propostsa para a POC inclui 4 aplicações e o uso de uma solução de mensageria - Kafka Topics,para tarefas assíncronas entre os módulos.

* order-application: incluindo o frontend em angular e backend 'principal' para a execução de pedidos no Spring Boot.
* payment-service: módulo de pagamento implementado através de microsserviço em Spring Boot.
* shipping-service: módulo de expedição em microsserviço implementado em Spring Boot.
* discovery-service: módulo de 'service discovery' em Spring Boot utilizado para gestão dos endpoints da solução.
* Topic em Kafka: mensageria para comunicação assíncrona.

## Riscos técnicos

* Carga excessiva com possibilidade de bloqueio de requisições: nesse caso preferi utilizar uma abordagem assíncrona nas tarefas mais críticas na comunicação entre os módulos usando tópics kafka.
* Número de instancias de serviços com possibilidade real de crescimento: nesse caso a abordagem utilizada foi a utilização de um 'service discovery' Eureka para centralizar essa 
tarefa. E também viabiliza a implantação de um balanceador de carga.
* Falha na notificação de problemas ao usuário em situações assíncronas: foi utilizada uma _pequena_ abordagem orientada a eventos no módulo *order* para acompanhar esses processos.  

## Suposições

* Não me concentrei no cadastro e manutenção de usuário e considerei essas informações preexistentes na aplicação tanto web quanto de serviço.Em outras palavas,seus dados já estavam previamente cadastrados.
* Os campos de pagamento e de expedição são bastante simplificados e para aplicações _mais reais_ essas informações devem ser mais refinadas.
* Não há filtros nos eventos enviados a partir do servidor(server sent events) ou seja,qualquer requisição pode escutar eventos alheios porém resolvi manter a funcionalidade mesmo assim para focar na funcionalidade de notificação em tempo real em si.Para uma situação mais _de prdução_ cabe um melhor refinamento.