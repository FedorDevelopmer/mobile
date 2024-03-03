//
//  SignInView.swift
//  lab1
//
//  Created by fedor on 28.02.24.
//

import Foundation
import SwiftUI
import Firebase

struct SignInView: View {
    @State private var login: String = ""
    @State private var password: String = ""
    @EnvironmentObject var appPage: PageState
    @EnvironmentObject var auth: AuthState
    var body: some View {
        TextField("Enter email",text: $login).padding()
        TextField("Enter password",text: $password).padding()
        Button (
        action: {
        InAppAuthorization().signIn(email:login,password:password)
        auth.authorized = true
        appPage.page = PageEnum.ITEMS
        },label: { Text("Sign In")})
        Button(action: {appPage.page = PageEnum.SIGNUP}, label: {Text("Already has account? Sign up")})
    }
    public init(login:String,password:String){
        self.password = password
        self.login = login
    }
}
