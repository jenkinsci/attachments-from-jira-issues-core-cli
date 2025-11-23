#!/usr/bin/env bash

function foo() {
    perl -e 'kill TERM => 0'
    echo "still in foo"
}

foo
echo "done"
