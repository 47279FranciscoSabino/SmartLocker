package project.smartlocker.http.utlis

/**
 * The Uris of the API
 */

object Uris {

    const val API = "/api"

    object Users {
        // admin
        const val GET_ALL_USERS = "/admin/users"                        //*
        const val GET_ALL_USERS_STATUS = "/admin/users/status"          //??
        const val DELETE = "/admin/user/{id}"                           //*
        const val EDIT_ROLE = "/admin/user/{id}"                        //*

        // global
        const val EDIT_STATUS = "/user/status/{userId}"                 //* mal

        // app
        const val LOGIN = "/login"                                      //login
        const val LOGOUT = "/logout"                                    //logout
        const val REGISTER = "/signup"                                  //signup

        const val PROFILE = "/profile"                                  //getProfile
        const val EDIT_USER = "/profile"                                //editUser
        const val USER_INFO = "/user/{id}"                              //getUserInfo

        //
        const val GET_BY_USERNAME = "/user"                             //getByUsername
        const val GET_BY_ID = "/users/{id}"                             //getUser
    }

    object Friends{
        // admin
        const val GET_FRIEND = "/admin/user/{id}/friend/{friendId}"     //*
        const val GET_FRIENDS ="/admin/user/{id}/friends"               //*

        // app
        const val GET_USER_FRIENDS = "/friends"                         //getFriendsList
        const val ADD_FRIEND = "/friend"                                //addFriend
        const val EDIT_FRIEND = "/friend/{friendId}"                    //editFriend
        const val REMOVE_FRIEND = "/friend/{friendId}"                  //removeFriend
    }

    object Module{
        //admin
        const val GET_ALL_MODULES = "/admin/modules"                    //*
        const val GET_ALL_MODULES_STATUS = "/admin/modules/status"      //?
        const val CREATE_MODULE = "/admin/module"                       //*
        const val UPDATE_MODULE = "/admin/module/{id}"                  //*
        const val DELETE_MODULE = "/admin/module/{id}"                  //*

        //
        const val GET_MODULE_BY_ID = "/module/{id}"                     //*

        // app
        const val GET_MAP = "/map"                                      //getMap
    }

    object Locker {
        //admin
        const val GET_ALL_LOCKERS = "/admin/lockers"
        const val GET_ALL_LOCKERS_STATUS = "/admin/lockers/status"
        const val CREATE_LOCKER = "/admin/locker"
        const val UPDATE_LOCKER = "/admin/locker/{id}"
        const val DELETE_LOCKER = "/admin/locker/{id}"

        //global
        const val GET_LOCKER_BY_ID = "/locker/{id}"
        const val GET_LOCKER_BY_HASH = "/locker/hash/{hash}"
    }

    object Hash{
        const val GET_HASH = "/hash/{hash}"
        const val CREATE_HASH = "/hash"

        //app
        const val SCAN = "/scan/{hash}"
    }

    object Trade {
        //admin
        const val GET_ALL_TRADE = "/admin/trades"
        const val GET_ALL_TRADE_STATUS = "/admin/trades/status"
        const val DELETE_TRADE = "/admin/trade/{id}"

        // app
        const val GET_TRADE = "/trade/{tradeId}"                        //getTradeInfo
        const val LOCKER_TRADE = "/trade/locker/{lockerId}"             //getLockerTrade
        const val NEW_TRADE = "/trade"                                  //newTrade
        const val EDIT_TRADE = "/trade/{tradeId}"                       //editTrade
        const val WITHDRAW = "/withdraw/{lockerId}"                     //confirmTrade
    }

    object History {
        //admin
        const val GET_BY_LOCKER = "/locker/{id}/history"

        // app
        const val USER_FULL_HISTORY = "/history"                        // getFullHistory
        const val SENDER_HISTORY = "/user/sent"                         // getSenderHistory
        const val RECEIVER_HISTORY = "/user/received"                   // getReceiverHistory
    }
}
