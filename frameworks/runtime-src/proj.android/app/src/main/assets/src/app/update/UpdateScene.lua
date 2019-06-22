local UpdateScene = class("UpdateScene", function()
    return display.newScene("UpdateScene")
end)

function UpdateScene:ctor()
    dump(device, "device", 8)
    display.newTTFLabel({text = "Hello, World", size = 64})
        :align(display.CENTER, display.cx, display.cy)
        :addTo(self)
end

function UpdateScene:onEnter()
end

function UpdateScene:onExit()
end

return UpdateScene