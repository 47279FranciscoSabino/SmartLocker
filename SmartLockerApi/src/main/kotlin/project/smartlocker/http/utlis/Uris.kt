package project.smartlocker.http.utlis

/**
 * The Uris of the API
 */

object Uris {

    const val HOME = "/api"

    object Users {
        const val GET_ALL_USERS = "/users"
        const val GET_BY_ID = "/users/{id}"         //
        const val CREATE_USER = "/user"             //
        const val UPDATE_USER = "/user/{id}"        //
        const val DELETE = "/user/{id}"

        const val LOGIN = "/auth/login"             //
        const val LOGOUT = "/auth/logout"           //
    }

    object Friends{
        const val GET_FRIENDS = "/user/{id}/friends"                //
        const val GET_FRIEND = "/user/{id}/friend/{friendId}"       //
        const val ADD_FRIEND = "/user/{id}/friend"                  //
        const val UPDATE_FRIEND = "/user/{id}/friend/{friendId}"    //
        const val DELETE_FRIEND = "/user/{id}/friend/{friendId}"    //
    }

    object Trade {
        const val GET_ALL_TRADE = "/trades"
        const val GET_TRADE_BY_ID = "/trade/{id}"                   //
        const val CREATE_TRADE = "/trade"                           //
        const val UPDATE_TRADE = "/trade/{id}"
        const val DELETE_TRADE = "/trade/{id}"
    }

    object Module{
        const val GET_ALL_MODULES = "/modules"
        const val GET_MODULE_BY_ID = "/module/{id}"
        const val CREATE_MODULE = "/module"
        const val UPDATE_MODULE = "/module/{id}"
        const val DELETE_MODULE = "/module/{id}"

        const val GET_MODULE_GEO = "/geo/{latitude}/{longitude}"    //
    }

    object Locker {
        const val GET_ALL_LOCKERS = "/lockers"
        const val GET_LOCKER_BY_ID = "/locker/{id}"
        const val CREATE_LOCKER = "/locker"
        const val UPDATE_LOCKER = "/locker/{id}"
        const val DELETE_LOCKER = "/locker/{id}"

        const val QR_VALIDATION = "/lockers/{id}/qr"                //
    }

    object History {
        const val USER_HISTORY = "/user/{id}/history"               //
        const val GET_BY_RECEIVER = "/receiver/{id}"                //
        const val GET_BY_SENDER = "/sender/{id}"                    //
        const val GET_BY_LOCKER = "/locker/{id}/history"
    }
}
