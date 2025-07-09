package project.smartlocker.http.utlis

/**
 * The Uris of the API
 */

object Uris {

    const val HOME = "/api"

    object Users {
        // admin
        const val GET_ALL_USERS = "/admin/users"
        const val GET_ALL_USERS_STATUS = "/admin/users/status"
        const val DELETE = "/admin/user/{id}"

        //
        const val VALIDATE_USER = "/user/{id}/status"
        const val GET_BY_USERNAME = "/user/{username}"
        const val GET_BY_ID = "/users/{id}"

        // global
        const val UPDATE_USER = "/user/{id}"
        const val CREATE_USER = "/user"

        const val LOGIN = "/auth/login"             //
        const val LOGOUT = "/auth/logout"           //
    }

    object Friends{
        //admin
        const val GET_FRIEND = "/admin/user/{id}/friend/{friendId}"
        const val GET_FRIENDS ="/admin/user/{id}/friends"

        //global
        const val GET_FRIENDS_INFO = "/user/{id}/friends"
        const val ADD_FRIEND = "/user/{id}/friend"
        const val UPDATE_FRIEND = "/user/{id}/friend/{friendId}"
        const val DELETE_FRIEND = "/user/{id}/friend/{friendId}"
    }

    object Module{
        //admin
        const val GET_ALL_MODULES = "/admin/modules"
        const val GET_ALL_MODULES_STATUS = "/admin/modules/status"
        const val CREATE_MODULE = "/admin/module"
        const val UPDATE_MODULE = "/admin/module/{id}"
        const val DELETE_MODULE = "/admin/module/{id}"

        //
        const val GET_MODULE_BY_ID = "/module/{id}"

        //global
        const val GET_MODULE_GEO = "/geo/{latitude}/{longitude}"
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
    }

    object Trade {
        //admin
        const val GET_ALL_TRADE = "/admin/trades"
        const val GET_ALL_TRADE_STATUS = "/admin/trades/status"
        const val DELETE_TRADE = "/admin/trade/{id}"

        //global
        const val GET_TRADE_BY_ID = "/trade/{id}"
        const val GET_PENDING_TRADE = "/trade/pending/{locker}"
        const val CREATE_TRADE = "/trade"
        const val UPDATE_TRADE = "/trade/{id}"
    }

    object History {
        //admin
        const val GET_BY_LOCKER = "/locker/{id}/history"

        //global
        const val USER_HISTORY = "/user/{id}/history"
        const val GET_BY_RECEIVER = "/receiver/{id}"
        const val GET_BY_SENDER = "/sender/{id}"
    }
}
