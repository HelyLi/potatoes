local Board = require "app.game.classic2048.module.Board"
local Config = require "app.game.classic2048.common.Config"

-- src/app/game/classic2048/module/Board.lua
local MainScene = class("MainScene", function()
    return display.newScene("MainScene")
end)
local BoardLayer

function MainScene:ctor()
    self:init()
end

function MainScene:onEnter()
end

function MainScene:onExit()
end

function MainScene:init()

    self:InitBoard()
    
    BoardLayer:addNodeEventListener(cc.NODE_TOUCH_EVENT, function(event)
		dump(event)
		if event.name == "began" then
            return self:onTouchBegan(event)
        elseif event.name == "move" then
            self:onTouchMoved(event)
        elseif event.name == "ended" then
            self:onTouchEnded(event)
        end
        
	end)
	BoardLayer:setTouchMode(cc.TOUCH_MODE_ONE_BY_ONE) -- default mode
	BoardLayer:setTouchEnabled(true)
	-- local dispatcher = cc.Director:getInstance():getEventDispatcher()

	-- -- 键盘事件
	-- local keylistener = cc.EventListenerKeyboard:create()
	-- keylistener:registerScriptHandler(onRelease, cc.Handler.EVENT_KEYBOARD_RELEASED) -- 响应安卓返回键
	-- dispatcher:addEventListenerWithSceneGraphPriority(keylistener, self)
	
	-- -- 触摸事件
	-- local touchlistener = cc.EventListenerTouchOneByOne:create()
	-- touchlistener:registerScriptHandler(function(touch, event) return self:onTouchBegan(touch, event) end, cc.Handler.EVENT_TOUCH_BEGAN)
	-- touchlistener:registerScriptHandler(function(touch, event) return self:onTouchMoved(touch, event) end, cc.Handler.EVENT_TOUCH_MOVED)
	-- touchlistener:registerScriptHandler(function(touch, event) return self:onTouchEnded(touch, event) end, cc.Handler.EVENT_TOUCH_ENDED)
	-- touchlistener:registerScriptHandler(function(touch, event) return self:onTouchCancelled(touch, event) end, cc.Handler.EVENT_TOUCH_CANCELLED)
	-- dispatcher:addEventListenerWithSceneGraphPriority(touchlistener, self)
end




local rectLen = display.height/4 -- 单元格长度
local Num2Color = Config.Num2Color -- 数字颜色
local MaxScore = 0 -- 历史最高分
local CurScore = 0 -- 当前分数 
local NumLabels = {} -- 数字组件
local NumBgRects = {} -- 数字背景色矩形框

local function createNum(idx, idy)
	local cx = (idx-1)*rectLen +rectLen/2 
	local cy = (idy-1)*rectLen +rectLen/2 
	local label = cc.Label:createWithSystemFont("", "Arial", 60)
	label:setPosition(cx, cy)
	label:addTo(BoardLayer, 2) 	
	return label
end

local function createLine(x1, y1, x2, y2, linewidth)	
	local line = cc.DrawNode:create()
	-- rgba: cc.c4b(210, 180, 152, 255)
	line:drawSegment(cc.p(x1, y1), cc.p(x2,y2), linewidth, cc.c4f(0.82, 0.7, 0.6, 1)) --  ('起点' , '终点' , '半线宽' , '填充颜色')
	BoardLayer:addChild(line, 3)
end

local function creatRect(idx, idy)
	local rect = cc.DrawNode:create()
	-- rgba: cc.c4b(207, 198, 189, 255)
	rect:drawSolidRect(cc.p((idx-1)*rectLen, (idy-1)*rectLen), cc.p(idx*rectLen, idy*rectLen), cc.c4f(0.81, 0.776, 0.74, 1))
	BoardLayer:addChild(rect, 1) 
	return rect
end

local function InitNumLabels()
	for i = 1, 5 do -- 5竖
		local linewidth = 4
		if i == 1 or i == 5 then 
			linewidth = 8
		end
		createLine((i-1)*rectLen, 0, (i-1)*rectLen, display.height, linewidth)
	end
	
	for j = 1, 5 do -- 5横
		local linewidth = 4
		if j == 1 or j == 5 then 
			linewidth = 8
		end
		createLine(0, (j-1)*rectLen, display.height, (j-1)*rectLen, linewidth)
	end

	for i = 1, 4 do 
		NumLabels[i] = {}
		NumBgRects[i] = {}
		for j = 1, 4 do
			NumLabels[i][j] = createNum(i, j)
			NumBgRects[i][j] = creatRect(i, j)	
		end
	end
end	

function MainScene:DrawBoard()
	local boarddata = Board.GetBoardData()
	if not boarddata then return end 
	
	for i = 1, 4 do 
		for j = 1, 4 do 
			NumBgRects[i][j]:clear()
			local num = boarddata[i][j]
			local numLabel = NumLabels[i][j]
			if num > 0 then 
				local rgbArr
				if num >= 4096 then 
					rgbArr = Num2Color[4096]
				else 
					rgbArr = Num2Color[num]
				end
				numLabel:setString(tonumber(num))
				NumBgRects[i][j]:drawSolidRect(cc.p((i-1)*rectLen, (j-1)*rectLen), cc.p(i*rectLen, j*rectLen), cc.c4f(rgbArr[1]/255, rgbArr[2]/255, rgbArr[3]/255, 1))

				if num == 2 or num == 4 then 
					numLabel:setColor(cc.c4b(0, 0, 0, 255))
				else 
					numLabel:setColor(cc.c4b(255, 255, 255, 255))
				end 	 	
			else 
				numLabel:setString("")
				NumBgRects[i][j]:drawSolidRect(cc.p((i-1)*rectLen, (j-1)*rectLen), cc.p(i*rectLen, j*rectLen), cc.c4f(0.81, 0.776, 0.74, 1))
			end
		end
	end	
end

function MainScene:AddNum(num)
	if Board.GetNumCount() < 16 then 
		Board.GenNewNum(num)
	end
end

function MainScene:ResetBoard()
	
	Board.InitBoardData()
	CurScore = 0
	self:AfterOperate(2, true)
end

function MainScene:AfterOperate(num, move)
	if move then 
		self:DrawBoard()
		-- self:RefreshScoreLayer()
		-- 新出来的数字 延迟0.5s显示
		self:AddNum(num)
		--scheduler.performWithDelayGlobal(function() self:DrawBoard() end, 0.3) -- 定时器:只执行一次
        self:runAction(cc.Sequence:create(cc.DelayTime:create(0.3), cc.CallFunc:create(handler(self, self.DrawBoard))))
    else 
		if Board.isBoardEnd() then -- 重置确认弹框
			
		end
	end
end

local firstX = 0
local firstY = 0
function MainScene:onTouchBegan(event)
	-- 纪录触摸起始点坐标
	local beginPoint = event--touch:getLocation()
	firstX = beginPoint.x
	firstY = beginPoint.y
	if rankFlag then return false end 
	return true
end 

function MainScene:onTouchMoved(event)
	return true
end 

function MainScene:onTouchEnded(event)
	-- 纪录触摸终点坐标
	local endPoint = event--touch:getLocation()
	local endX = firstX - endPoint.x
	local endY = firstY - endPoint.y

	-- 看是横向移动大还是纵向滑动大
	local flag = false -- 滑动后发现有合并的 则新增数字
	local mergeScore = 0
	local beforeMaxNum = Board.GetMaxNum()
	local mergePos = {} -- 合并数字的位置
	if math.abs(endX) > math.abs(endY) then 
		if math.abs(endX) > 5 then -- 滑动太少不算
			if endX > 0 then 
				 flag, mergeScore, mergePos = Board.OnLeft()
			else 
				flag, mergeScore, mergePos = Board.OnRight()
			end
		end		
	else 
		if math.abs(endY) > 5 then -- 滑动太少不算
			if endY > 0 then 
				flag, mergeScore, mergePos = Board.OnDown()
			else 
				flag, mergeScore, mergePos = Board.OnUp()
			end	
		end	
	end
	
	if flag then 
		for i, v in ipairs(mergePos) do 
			local label = NumLabels[v[1]][v[2]]
			local action1 = cc.ScaleTo:create(0.3,1.5)
			local action2 = cc.ScaleTo:create(0.3,1)
			label:runAction(cc.Sequence:create(action1,action2))  -- 先放大再缩小
		end 	

		local afterMaxNum, idx, idy = Board.GetMaxNum() 
		if afterMaxNum > beforeMaxNum and afterMaxNum >= 4 then 
			local particle = cc.ParticleExplosion:create() -- 爆炸粒子效果
			local cx = (idx-1)*rectLen +rectLen/2 
			local cy = (idy-1)*rectLen +rectLen/2 
			particle:setPosition(cx, cy)
			BoardLayer:addChild(particle, 5)
			scheduler.performWithDelayGlobal(function() particle:removeFromParent() end, 1) -- 定时器:只执行一次

			-- GameMusic.playEffect("merge_special.mp3")
		else
			-- GameMusic.playEffect("merge_normal.mp3")
		end
	end

	CurScore = CurScore + mergeScore -- 分数增加
	self:AfterOperate(1, flag)
end

function MainScene:onTouchCancelled(touch, event)
end

local function onRelease(keyCode, event)
	if keyCode == cc.KeyCode.KEY_BACK or keyCode == cc.KeyCode.KEY_Q then	-- KEY_BACK android / KEY_Q windows
		--ExitGame()
	elseif keyCode == cc.KeyCode.KEY_HOME then
		-- ExitGame()
	end
end

function MainScene:InitBoard()
	local color = cc.c4b(207, 198, 189, 0)
    BoardLayer = cc.LayerColor:create(color)
	BoardLayer:setContentSize(cc.size(display.height, display.height))
	BoardLayer:setPosition(cc.p(0, 0))
	BoardLayer:setVisible(true)
	self:addChild(BoardLayer, 1)
	
	-- local color1 = cc.c4b(130, 36, 94, 255)
    -- ScoreLayer = cc.LayerColor:create(color1)
	-- ScoreLayer:setContentSize(cc.size(display.width - display.height, display.height))
	-- ScoreLayer:setPosition(cc.p(display.height, 0))
	-- ScoreLayer:setVisible(false)
	-- self:addChild(ScoreLayer, 1)

	InitNumLabels()
	-- InitScoreLayer()	
	self:DrawBoard()
	if not Board.GetBoardData() then 
		self:ResetBoard()
	end
	
	-- GameMusic.playMusic("music_bg.mp3", true)
end

return MainScene