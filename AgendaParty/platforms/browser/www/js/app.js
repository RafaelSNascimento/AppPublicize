angular.module('App', ['ui.router',ngSweetAlert2,'luegg.directives','ngMap'])
    .factory('httpInterceptor', function httpInterceptor ($q, $window, $location) {
      return function (promise) {
          var success = function (response) {
              return response;
          };

          var error = function (response) {
            console.log($location.url());
            console.log(response.error);
            if(response.status == 401 && $location.url() != "/"){
                $location.url("/");
            }
              return $q.reject(response);
          };

          return promise.then(success, error);
      };
    })
    .service('todoService', function($q) {
        db = window.openDatabase('TodoDB', '1.0', 'TodoDB', 200000);
            this.config = function(fun, args){
                var arg = args;
                db.transaction(function(tx) {
                    tx.executeSql('CREATE TABLE IF NOT EXISTS configuracao (Id integer primary key, serverIp varchar(255) DEFAULT NULL, notificationFlag varchar(3) DEFAULT NULL, userName varchar(255) DEFAULT NULL)');
                });
                switch(fun) {
                    case 'changeIp':
                        db.transaction(function(tx) {
                            tx.executeSql("insert or ignore into configuracao(Id) values(0)",[], function(){
                                tx.executeSql("update configuracao set serverIp='"+args+"' where Id = 0;");
                            });
                        })
                        break;
                    case 'subscribe':
                        FCMPlugin.subscribeToTopic('ConvidadoImportante');
                        db.transaction(function(tx) {
                            tx.executeSql("insert or ignore into configuracao(Id) values(0)",[], function(){
                                tx.executeSql("update configuracao set notificationFlag='"+args+"' where Id = 0;");
                            });
                        })
                        break;
                    case 'unsubscribeFromTopic':
                        FCMPlugin.unsubscribeFromTopic('ConvidadoImportante');
                        db.transaction(function(tx) {
                            tx.executeSql("insert or ignore into configuracao(Id) values(0)",[], function(){
                                tx.executeSql("update configuracao set notificationFlag='"+args+"' where Id = 0;");
                            });
                        })
                        break;
                    case 'userName':
                        db.transaction(function(tx) {
                            tx.executeSql("insert or ignore into configuracao(Id) values(0)",[], function(){
                                tx.executeSql("update configuracao set userName='"+args+"' where Id = 0;");
                            });
                        })
                        break;
                    default:
                        var deferred, result = [];
                        deferred = $q.defer();
                        db.transaction(function(tx) {
                            tx.executeSql("select * from configuracao", [], function(tx, res) {
                            result.push(res.rows[0]);
                            deferred.resolve(result);
                            });
                        })
                        return deferred.promise;
                }
            }
          
    })
    .controller('mainController', ['$scope', '$http', '$state', 'todoService', function($scope, $http, $state, todoService) {
        $scope.dashboard={
            menu:"",
            user:{},
            subTittle:"",
            sessionUser: "",
            token:""
        }
        todoService.config('all','').then(function(item){
            if(item[0]){
                $scope.ipServidor = item[0].serverIp;
                $scope.user = item[0].userName;
                $scope.notificacao = { flag:true};
            }else{
                $scope.notificacao = { flag:false};
            }
        });
        $scope.username = function(name){
            $scope.user = name;
            todoService.config('userName', name);
        }
        $scope.notificacaoChange = function(){
            if($scope.notificacao.flag){
               todoService.config('subscribe', true);
            }else{
                todoService.config('unsubscribeFromTopic', false);
            }
            
        }
        $scope.setIpServidor = function(value){
            $scope.ipServidor = value;
            todoService.config('changeIp', value);
        }

        $scope.loading = false;
        $scope.eventos = "";
 
        $scope.configuracao = function() {

            $state.go('configuracao');

        }
        $scope.removeAll = function() {
            todoService.removeAll().then(function() {
                todoService.getItems($scope.createEvento, 'evento', ' order by data').then(function(items) {
                    $scope.eventos = items;
                });
                todoService.getItems($scope.createBatalhao, 'unidade', ' order by nome').then(function(items) {
                    $scope.batalhao = items;
                });
                todoService.getItems($scope.createAutoridade, 'autoridades', ' order by nome').then(function(items) {
                    if (items[0] == null) $scope.autoridades = false;
                });
                todoService.getItems($scope.createConvidados, 'convidados', ' order by Id').then(function(items) {
                    if (items[0] == null) $scope.convidados = false;
                });
            });

        }

    }])
    .controller('cadastrarUsuarioController', ['$scope', '$http', '$state', 'todoService', function($scope, $http, $state, todoService) {
        $scope.dashboard.menu = "cadastrarusuario";
        $scope.dashboard.subTittle = "Cadastrar Usuário.";
        $scope.dashboard.sessionUser = "";
        $scope.cadastrarUsuario = function() {
            $http({
                method: 'POST',
                url: 'http://localhost:8080/springRestSecurity/user/new',
                headers:{
                    "Content-Type": "application/json"
                },
                data: {"cpf":$scope.dashboard.user.cpf, "username":$scope.dashboard.user.username, "password": $scope.dashboard.user.password, "role":$scope.dashboard.user.role}
            }).then(function successCallback(response) {
                console.log(response);
                console.log("entrou aqui");
                console.log(response.status);
                if(response.status != 400){
                    console.log("herou")
                    $scope.dashboard.user = response.data;
                    swal("Sucesso!","Conta cadastrada com Sucesso", "success");
                }else{
                    swal("Ops...","Já possui esses dados no banco de dados", "error");
                }
                

            }, function errorCallback(response) {
                console.log(response);
                swal("Opss....", "Dados Invalidos","error");
            });

        }
    }])
    .controller('loginController', ['$scope', '$http', '$state', 'todoService', function($scope, $http, $state, todoService) {
        $scope.dashboard.menu = "";
        $scope.dashboard.subTittle = "Sua agenda de Eventos.";
        $scope.dashboard.sessionUser = "";

        $scope.login = function() {
            $http({
                method: 'POST',
                url: 'http://localhost:8080/springRestSecurity/login',
                headers:{
                    "Content-Type": "application/json"
                },
                data: $scope.dashboard.user
            }).then(function successCallback(response) {
                console.log(response);
                $scope.dashboard.user = response.data;
                console.log(response.headers()['token']);
                $scope.dashboard.token = response.headers()['token'];
                if(response.data.autorizacoes[0].authority == "ROLE_ADMIN"){
                    $state.go('homeAdmin');
                }else{
                    $state.go('homeUser');
                }

            }, function errorCallback(response) {
                swal("Opss","Usuario não encontrado","error");
            });

        }
    }])
    .controller('homeAdminController', ['$scope', '$http', '$state', 'todoService', function($scope, $http, $state, todoService) {
        $scope.dashboard.menu = "";
        $scope.dashboard.subTittle = "Sua agenda de Eventos.";
        $scope.dashboard.sessionUser = "admin";
        $scope.eventos = [];
        $scope.filtrarEventos = function(){
            $http({
                url: "http://localhost:8080/springRestSecurity/evento/get", 
                method: "GET",
                params: $scope.filtro,
                headers:{
                    "Authorization":$scope.dashboard.token
                }
             })
            .then(function successCallback(response) {
                $scope.eventos = response.data;

            }, function errorCallback(response) {
                console.log(response);
            });
        }
    }])
    .controller('eventosAdminController', ['$scope', '$http', '$state', 'todoService', function($scope, $http, $state, todoService) {
        $scope.dashboard.menu = "empresaAdmin";
        $scope.dashboard.subTittle = "Sua Empresa no AgendaParty.";
        $scope.cadastrarEmpresa = function() {
            $http({
                method: 'POST',
                url: 'http://localhost:8080/springRestSecurity/empresa/new',
                headers:{
                    "Content-Type": "application/json",
                    "Authorization":$scope.dashboard.token
                },
                data: {"nome":$scope.dashboard.user.empresa.nome, "razaoSocial": $scope.dashboard.user.empresa.razaoSocial}
            }).then(function successCallback(response) {
                console.log(response);
                console.log("entrou aqui");
                console.log(response.status);
                if(response.status != 400){
                    console.log("herou")
                    $scope.dashboard.user.empresa = response.data;
                    swal("Sucesso!","Empresa cadastrada com Sucesso", "success");
                }else{
                    swal("Ops...","Já possui esses dados no banco de dados", "error");
                }
                

            }, function errorCallback(response) {
                console.log(response);
                swal("Opss....", "Dados Invalidos","error");
            });

        }
        $scope.alterarEmpresa = function() {
            console.log($scope.dashboard.user.empresa);
            $http({
                method: 'POST',
                url: 'http://localhost:8080/springRestSecurity/empresa/update',
                headers:{
                    "Content-Type": "application/json",
                    "Authorization":$scope.dashboard.token
                },
                data: {"nome":$scope.dashboard.user.empresa.nome, "razaoSocial": $scope.dashboard.user.empresa.razaoSocial, "id": $scope.dashboard.user.empresa.id}
            }).then(function successCallback(response) {
                console.log(response);
                console.log("entrou aqui");
                console.log(response.status);
                if(response.status != 400){
                    console.log("herou");
                    swal("Sucesso!","Empresa alterada com Sucesso", "success");
                }else{
                    swal("Ops...","Já possui esses dados no banco de dados", "error");
                }
                

            }, function errorCallback(response) {
                console.log(response);
                swal("Opss....", "Dados Invalidos","error");
            });

        }
        $scope.deletarEmpresa = function() {
            $http({
                method: 'DELETE',
                url: 'http://localhost:8080/springRestSecurity/empresa/delete/'+$scope.dashboard.user.empresa.id,
                headers:{
                    "Content-Type": "application/json",
                    "Authorization":$scope.dashboard.token
                }
            }).then(function successCallback(response) {
                console.log(response);
                console.log("entrou aqui");
                console.log(response.status);
                if(response.status != 400){
                    console.log("herou");
                    $scope.dashboard.user.empresa = response.data;
                    swal("Sucesso!","Empresa Excluida com Sucesso", "success");
                }else{
                    swal("Ops...","Dados Inválidos", "error");
                }
                

            }, function errorCallback(response) {
                console.log(response);
                swal("Opss....", "Dados Invalidos","error");
            });

        }

    }])
    .controller('homeUserController', ['$scope', '$http', '$state', 'todoService','NgMap', function($scope, $http, $state, todoService,NgMap) {
        $scope.dashboard.menu = "";
        $scope.dashboard.subTittle = "Sua agenda de Eventos.";
        $scope.dashboard.sessionUser = "user";
        $scope.filtro = {
            nome:"",
            cidade:"",
            data:""
        }
        $scope.eventos = [];
        console.log($scope.dashboard.token);
        $scope.filtrarEventos = function(){
            $http({
                url: "http://localhost:8080/springRestSecurity/evento/get", 
                method: "GET",
                params: $scope.filtro,
                headers:{
                    "Authorization":$scope.dashboard.token
                }
             })
            .then(function successCallback(response) {
                $scope.eventos = response.data;

            }, function errorCallback(response) {
                console.log(response);
            });
        }
    }])
    .controller('contaUserController', ['$scope', '$http', '$state', 'todoService','$location', function($scope, $http, $state, todoService, $location) {
        $scope.dashboard.menu = "contaUser";
        $scope.dashboard.subTittle = "Sua agenda de Eventos.";

        $scope.updateUser = function() {
            console.log($scope.dashboard.user);
            $http({
                method: 'POST',
                url: 'http://localhost:8080/springRestSecurity/user/update',
                headers:{
                    "Content-Type": "application/json",
                    "Authorization": $scope.dashboard.token
                },
                data: {"id":$scope.dashboard.user.id,"cpf":$scope.dashboard.user.cpf, "nome":$scope.dashboard.user.nome, "senha": $scope.dashboard.user.senha}
            }).then(function successCallback(response) {
                console.log(response);
                $scope.dashboard.user = response.data;
                swal("Sucesso!","Conta Alterada com Sucesso", "success");

            }, function errorCallback(response) {
                console.log(response);
                swal("Ops..", "Preencha todos os Campos", "error")
            });

        }
        $scope.deleteUser = function() {
            console.log($scope.dashboard.user);
            $http({
                method: 'DELETE',
                url: 'http://localhost:8080/springRestSecurity/user/delete/'+$scope.dashboard.user.id,
                headers:{
                    "Content-Type": "application/json",
                    "Authorization": $scope.dashboard.token
                }
            }).then(function successCallback(response) {
                $scope.dashboard.user = response.data;
                swal("Sucesso!","Conta Excluida com Sucesso", "success");
                $location.url("/");
                $scope.dashboard.user = {};

            });

        }

    }])
    .config(function($stateProvider, $urlRouterProvider, $httpProvider) {
        $httpProvider.responseInterceptors.push('httpInterceptor');
        $urlRouterProvider.otherwise('/');
        $stateProvider
        .state('index', {
            url: '/',
            controller: 'loginController'
        })
        .state('cadastrar', {
            url: '/cadastrarusuario',
            controller: 'cadastrarUsuarioController',
            templateUrl: 'partials/cadastrarUsuario.html'
        })
        .state('homeAdmin', {
            url: '/homeAdmin',
            controller: 'homeAdminController',
            templateUrl: 'partials/homeAdmin.html'
        })
        .state('homeUser', {
            url: '/homeUser',
            controller: 'homeUserController',
            templateUrl: 'partials/homeUser.html'
        })
        .state('contaUser',{
            url: '/contaUser',
            controller: 'contaUserController',
            templateUrl: 'partials/contaUser.html'
        })
        .state('empresaAdmin',{
            url:'/empresaAdmin',
            controller: 'eventosAdminController',
            templateUrl: 'partials/eventosAdmin.html'
        })
    })
    .run(function($rootScope) {
        $rootScope.$on('$stateChangeSuccess', function() {
           document.body.scrollTop = document.documentElement.scrollTop = 0;
        });
    });
angular.module('App').config(['$compileProvider',
    function($compileProvider) {
        $compileProvider.aHrefSanitizationWhitelist(/^\s*(https?|ftp|mailto|file):/);
    }
]);
