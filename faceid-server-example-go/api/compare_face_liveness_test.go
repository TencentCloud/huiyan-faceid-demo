package api

import (
	"fmt"
	"testing"
)

func TestCompareFaceLiveness(t *testing.T) {
	err := CompareFaceLiveness()
	if nil != err {
		fmt.Printf("error: %+v", err)
	}
}
