import React from "react"
import { Navigate,Outlet } from "react-router-dom";

const ProtectedRoute=({isAuthenticated,children,adminRoute,isAdmin,isSuperAdmin})=>{
    if(!isAuthenticated)
    {
        return <Navigate to={"/login"}/>
    }
    if(adminRoute && !isAdmin)
    {
        return <Navigate to={"/"}/>
    }
    if(adminRoute && !isSuperAdmin)
    {
        return <Navigate to={"/"}/>
    }
    
    return children ? children :<Outlet/>;
}

export default ProtectedRoute;