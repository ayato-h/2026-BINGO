# BINGO (Console Bingo Game 2026) 最終設計書

# 1. アプリ概要

| 項目 | 内容 |
| --- | --- |
| アプリ名 | BINGO (Console Bingo Game 2026) |
| 開発言語 | Java |
| 実行環境 | コンソール |
| ゲーム形式 | 1人用BINGOゲーム |
| カードサイズ | 3×3、5×5、7×7 |
| データ保存 | なし（実行中のみメモリ上で管理） |

# 2. コンセプト

コンソール上で手軽に遊べるBINGOゲーム。

基本的なBINGOのルールに加えて、プレイスタイルの異なる3つのモードを用意し、  
自分の記録を確認しながら繰り返し遊べるゲームを目指す。


# 3. ゲームモード

## NORMAL

通常のBINGOを楽しむモード。

BINGOになるまでゲームを続け、
BINGO達成までにかかったターン数を記録する。

### 特徴

- BINGOになるまでゲームを続ける
- BINGO達成までのターン数を記録
- 3×3、5×5、7×7それぞれのBEST TURNを記録
- ターン数を基にスコアを計算
- 過去最高記録を更新するとNEW表示を行う

## ENDLESS

BINGOを達成してもゲームを終了せず、
同じカードで次のBINGOを目指し続けるモード。

最後のBINGOまでゲームを続け、
ゲーム全体のターン数とBINGO回数を記録する。

### 特徴

- BINGO達成後もゲームを継続
- BINGOするたびにBINGO回数を加算
- 前回のBINGOから何ターンでBINGOしたかを記録
- ゲーム終了時にTOTAL BINGOを表示
- ゲーム全体のターン数を記録
- ENDLESSモード専用のBEST TURNを記録

## CHALLENGE

制限ターン以内にBINGOを達成できるかを競うモード。

カードサイズによって制限ターンが決まり、
制限ターンを超える前にBINGOできればクリアとなる。

### 制限ターン

| カードサイズ | 制限ターン |
| --- | ---: |
| 3×3 | 9ターン |
| 5×5 | 25ターン |
| 7×7 | 49ターン |

### 特徴

- カードサイズごとに制限ターンを設定
- 制限ターン以内にBINGOできればCLEAR
- 制限ターンまでにBINGOできなければFAILED
- スコア計算は行わない
- NORMAL、ENDLESSとは異なり、限られたターン内でBINGOを達成することを目的とする
- クリア状況をプレイヤー情報として記録

# 4. ゲームの流れ

```text
開始
 ↓
PLAYER SETUP
 ↓
プレイヤー名入力
 ↓
タイトル表示
 ↓
MAIN MENU
 ├─ 1. ゲーム開始
 │    ↓
 │   カードサイズ選択
 │    ↓
 │   現在のモードでゲーム開始
 │    ↓
 │   BINGOカード生成
 │    ↓
 │   数字抽選
 │    ↓
 │   カードの数字を開く
 │    ↓
 │   カード表示
 │    ↓
 │   リーチ・BINGO判定
 │    ↓
 │   モードごとの条件を判定
 │    ↓
 │   ゲーム終了
 │    ↓
 │   RESULT表示
 │
 ├─ 2. 設定
 │    ↓
 │   名前変更
 │   モード変更
 │
 ├─ 3. 遊び方
 │    ↓
 │   ルール表示
 │
 └─ 0. GAME CLOSED
      ↓
     終了
```

# 5. BINGOカード

## カードサイズ

3×3、5×5、7×7の3種類から選択できる。

奇数サイズのカードでは中央のマスをFREEとする。

## 数字

1～99の数字からランダムにカードを生成する。

カード内の数字は重複しない。

## マスの状態

| 状態 | 表示 |
| --- | --- |
| 未開放 | 数字 |
| 開放済み | `//` |
| FREE | `FREE` |

開放済みのマスは状態に応じて色を変更する。

| 状態 | 色 |
| --- | --- |
| 通常の開放マス | YELLOW |
| リーチになっているマス | ORANGE |
| BINGOになっているマス | RED |

# 6. リーチ判定

縦・横・斜めのいずれかの列で、
FREEを含めて残り1マスになるとリーチと判定する。

リーチとなった列に含まれる開放済みマスおよびFREEマスを
ORANGEで表示する。

# 7. BINGO判定

以下のいずれか1列がすべて開放されるとBINGOと判定する。

- 横
- 縦
- 左上から右下の斜め
- 右上から左下の斜め

BINGOになった列に含まれる開放済みマスおよびFREEマスを
REDで表示する。

# 8. プレイヤー情報

プレイヤー情報は`Player`クラスで管理する。

| 項目 | 内容 |
| --- | --- |
| `name` | プレイヤー名 |
| `mode` | 現在選択しているゲームモード |
| `bestTurn3x3` | 3×3 NORMALのBEST TURN |
| `bestTurn5x5` | 5×5 NORMALのBEST TURN |
| `bestTurn7x7` | 7×7 NORMALのBEST TURN |
| `endlessTurn` | ENDLESSのBEST TURN |
| `challengeCleared` | CHALLENGEのクリア状況 |

BEST TURNは、過去の記録より少ないターン数でBINGOすると更新する。

# 9. スコア

## NORMAL

NORMALモードではBINGO達成までのターン数を使用してスコアを計算する。

カードサイズによって基準値を変え、
少ないターン数でBINGOするほど高いスコアになるようにする。

## ENDLESS

ENDLESSモードではスコア計算を行わず、
BINGO回数とターン数を記録する。

## CHALLENGE

CHALLENGEモードではスコア計算を行わない。

制限ターン内でBINGOを達成できたかどうかを結果として表示する。

# 10. 結果表示

## NORMAL

BINGO達成後に以下の情報を表示する。

```text
┌─ RESULT ─────────────────────────────┐
│ PLAYER      : プレイヤー名
│ SCORE       : スコア
│ BEST TURN   : BEST TURN
│ TURN        : 今回のターン数
└──────────────────────────────────────┘
```

BEST TURNを更新した場合は`NEW`を表示する。

## ENDLESS

ゲーム終了時に以下の情報を表示する。

```text
┌─ RESULT ─────────────────────────────┐
│ PLAYER      : プレイヤー名
│ TOTAL BINGO : BINGO回数
│ BEST TURN   : BEST TURN
│ TURN        : 総ターン数
└──────────────────────────────────────┘
```

## CHALLENGE

ゲーム終了時に以下の情報を表示する。

```text
┌─ RESULT ─────────────────────────────┐
│ PLAYER      : プレイヤー名
│ LIMIT TURN  : 制限ターン
│ TURN        : 実際のターン数
│ RESULT      : CLEAR / FAILED
└──────────────────────────────────────┘
```

# 11. クラス設計

| クラス | 役割 |
| --- | --- |
| `Main` | プログラムの開始と全体制御 |
| `Game` | ゲームの進行とモードごとの処理 |
| `BingoCard` | BINGOカードの生成・表示・マスの管理 |
| `BingoService` | リーチ・BINGOの判定 |
| `NumberGenerator` | 抽選数字の生成・管理 |
| `ScoreService` | NORMALモードのスコア計算・BEST TURN更新 |
| `Player` | プレイヤー情報・各種記録の保持 |
| `Menu` | タイトル・メニュー・結果画面などの表示 |
| `Input` | 入力受付と入力チェック |
| `Color` | コンソールの文字色を管理 |

# 12. パッケージ構成

```text
src
└── bingo
    ├── Main.java
    ├── game
    │   ├── Game.java
    │   ├── BingoCard.java
    │   └── NumberGenerator.java
    ├── model
    │   └── Player.java
    ├── service
    │   ├── BingoService.java
    │   └── ScoreService.java
    └── util
        ├── Input.java
        ├── Menu.java
        └── Color.java
```

# 13. 役割分担

## BingoCard.java

BINGOカードそのものを管理するクラス。

- カードの生成
- カードの表示
- 抽選数字によるマスの開放

BINGOやリーチの判定処理は`BingoService`に分離する。

## BingoService.java

BINGOに関する判定を担当するクラス。

- BINGO判定
- BINGOになったマスの判定
- リーチになったマスの判定
- 列・行・斜めの判定

## ScoreService.java

ゲーム結果に関する処理を担当するクラス。

- NORMALモードのスコア計算
- BEST TURNの更新

CHALLENGEモードのスコア計算は行わない。

# 14. モードの目的

| モード | 目的 |
| --- | --- |
| NORMAL | 少ないターンでBINGOして高スコア・BEST TURNを目指す |
| ENDLESS | 何回BINGOできるか、どれだけ長く続けられるかを楽しむ |
| CHALLENGE | 制限ターン以内にBINGOできるか挑戦する |

# 15. 最終的なゲーム設計

```text
                BINGO
                  │
       ┌──────────┼──────────┐
       ↓          ↓          ↓
    NORMAL      ENDLESS    CHALLENGE
       │          │          │
       ↓          ↓          ↓
   高スコア      BINGO回数   制限ターン
   BEST TURN     総ターン    CLEAR / FAILED
```

3つのモードでそれぞれ異なる遊び方を用意することで、
同じBINGOカードを使用しながら異なる目的でプレイできる構成とした。
