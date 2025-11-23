#!/usr/bin/env ruby

require 'faraday'

conn = Faraday.new(:url => "http://localhost:8080/") do |conn|
  conn.request :multipart
  conn.request :url_encoded
  conn.adapter :net_http
end

param = {p1: "foo"}
param[:f1] = Faraday::UploadIO.new('test.txt', 'application/octet-stream')

conn.post '/jenkins/job/kick_test/buildWithParameters', param
