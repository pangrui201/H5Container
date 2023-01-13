//notation: js file can only use this kind of comments
//since comments will cause error when use in webview.loadurl,
//comments will be remove by java use regexp
(function() {
   console.log('jsframework loading');
    if (window.AlipayJSBridge) {
        return;
    }
    var responseCallbacks = {};
    var uniqueId = 1;
    function send(data, responseCallback) {
        _doSend({
            data
        }, responseCallback);
    }

    function callHandler(requestName, data, responseCallback) {
        _doSend({
            requestName,
            data
        }, responseCallback);
    }

    //sendMessage add message, 触发native处理 sendMessage
    function _doSend(requestMessage, responseCallback) {
        if (responseCallback) {
            var callbackId = 'cb_' + (uniqueId++) + '_' + new Date().getTime();
            responseCallbacks[callbackId] = responseCallback;
            requestMessage.messageId = callbackId;
        }
        console.log('jsframework request to native',JSON.stringify(requestMessage));
        window._uplusNative.exec(JSON.stringify(requestMessage));
    }

  //提供给native调用,receiveMessageQueue 在会在页面加载完后赋值为null,所以
    //提供给native使用,
    function _handleMessageFromNative(responseMessageJSON) {
     console.log('jsframework _handleMessageFromNative: ' + responseMessageJSON);
        setTimeout(function() {
            var message = JSON.parse(responseMessageJSON);
            var responseCallback;
            //java call finished, now need to call js callback function
            if (message.messageType == "response") {
                responseCallback = responseCallbacks[message.messageId];
                if (!responseCallback) {
                    return;
                }
                responseCallback(message.data);
                delete responseCallbacks[message.messageId];
            } else if(message.messageType == "event"){
                sendEventToDocument(message.eventName,message.data);
            }
        });
    }

    function sendEventToDocument(eventName, data) {
        var event = document.createEvent('Events');
        event.initEvent(eventName, false, false);
        if (data) {
          data = {'data': data};
          for (var i in data) {
            if (Object.prototype.hasOwnProperty.call(data, i)) {
              event[i] = data[i];
            }
          }
        }
        console.log(`jsframework sendEventToDocument ${eventName}`,JSON.stringify(event));
        document.dispatchEvent(event);
    }


    window.AlipayJSBridge = {
        call: callHandler,
        _handleMessageFromNative: _handleMessageFromNative
    };
    console.log('jsframework ready');
    sendEventToDocument('AlipayJSBridgeReady');
})();