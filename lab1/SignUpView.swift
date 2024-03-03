//
//  SignUpView.swift
//  lab1
//
//  Created by fedor on 29.02.24.
//

import Foundation
import SwiftUI
import Firebase

struct SignUpView: View {
    @State private var login: String = ""
    @State private var password: String = ""
    @EnvironmentObject var appPage: PageState
    @EnvironmentObject var auth: AuthState
    var body: some View {
        Text("Sign Up").padding()
        TextField("Enter email",text: $login).padding()
        TextField("Enter password",text: $password).padding()
        Button (
        action: {
        InAppAuthorization().signUp(email:login,password:password)
        auth.authorized = true
        appPage.page = PageEnum.ITEMS
        },label: { Text("Sign Up")})
    }
    public init(login:String,password:String){
        self.password = password
        self.login = login
    }
}
