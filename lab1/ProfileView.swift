//
//  ProfileView.swift
//  lab1
//
//  Created by fedor on 2.03.24.
//

import Foundation
import SwiftUI

struct UserProfile : View {
    @State private var isDelete: Bool = false
    @State private var isLogOut: Bool = false
    @EnvironmentObject var appPage: PageState
    @EnvironmentObject var auth: AuthState
    
    var body : some View {
        Spacer()
        VStack{
            Text("Name")
            Text("Lastname")
            Text("Birth date")
            Text("Sex")
            Text("Country")
            Text("City")
            Text("Address")
            Text("Phone number")
            Text("Mail index")
            Text("Email")
            
        }
        Button(action: {isDelete = true}, label: {
            Text("Delete Account")
                .foregroundColor(.red)
        }).alert(isPresented: $isDelete, content: {
            Alert(
                title: Text("Account deletion"),
                message: Text("Are you sure to delete your account(all information will be lost, you cannot undo this action)?"),
                primaryButton: .destructive(Text("Delete")){
                    //removing firebase account + log out
                },
                secondaryButton: .cancel()
            )
        })
        Button(action:{
            isLogOut = true
        },label:{
            Text("Log Out")
                .foregroundColor(.red)
        }).alert(isPresented: $isLogOut, content: {
            Alert(
                title: Text("Log Out"),
                message: Text("Are you sure to log out of your account?"),
                primaryButton: .destructive(Text("Log Out")){
                    //log out
                },
                secondaryButton: .cancel()
            )
        })
        Spacer()
        HStack{
            Spacer()
            Button(action: /*@START_MENU_TOKEN@*/{}/*@END_MENU_TOKEN@*/, label: {
                VStack{
                    Image(systemName: "person.circle")
                        .resizable()
                        .scaledToFit()
                    Text("Profile")
                        .font(.system(size:14))
                }
            }).padding(EdgeInsets(top: 0, leading: 5, bottom: 0, trailing: 5))
            .frame(height:50)
            Spacer()
            Button(action: {appPage.page = PageEnum.ITEMS}, label: {
                VStack{
                    Image(systemName: "list.bullet")
                        .resizable()
                        .scaledToFit()
                    Text("Items")
                        .font(.system(size:14))
                }
                    
            }).padding(EdgeInsets(top: 0, leading: 5, bottom: 0, trailing: 5))
            .frame(height:50)
            Spacer()
            Button(action: {appPage.page = PageEnum.FEATURED}, label: {
                VStack{
                    Image(systemName: "star.circle")
                        .resizable()
                        .scaledToFit()
                    Text("Featured")
                        .font(.system(size:14))
                }
                    
            }).padding(EdgeInsets(top: 0, leading: 5, bottom: 0, trailing: 5))
            .frame(height:50)
            Spacer()
        }.frame(height: 50, alignment: .bottom)
    }
}
